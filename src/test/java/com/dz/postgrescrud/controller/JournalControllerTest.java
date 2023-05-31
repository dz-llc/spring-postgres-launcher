package com.dz.postgrescrud.controller;

import com.dz.postgrescrud.auth.AuthenticationService;
import com.dz.postgrescrud.auth.RegisterRequest;
import com.dz.postgrescrud.configuration.JwtService;
import com.dz.postgrescrud.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.dz.postgrescrud.user.Role.MANAGER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JournalControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AuthenticationService service;

    @Autowired
    private JwtService jwtService;

    public JournalControllerTest() {
    }

    public String createToken(User user) {
        return jwtService.generateToken(user);
    }

    @Test
    public void testCreateJournal() throws Exception {
        var testManager = RegisterRequest.builder()
                .firstname("TestFirstname")
                .lastname("TestLastname")
                .email("test@mail.com")
                .password("password")
                .role(MANAGER)
                .build();
        String token = service.register(testManager).getAccessToken();
        mvc.perform(post("/api/v1/journal/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("Test Journal Entry")));
    }

    @Test
    public void testCreateToken() throws Exception {
        // This user is created at startup in PostgrescrudApplication.java
        User manager = User.builder()
                .firstname("Admin")
                .lastname("Admin")
                .email("manager@mail.com")
                .password("password")
                .role(MANAGER)
                .build();

        String token = createToken(manager);

        mvc.perform(post("/api/v1/journal/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("Test Journal Entry")));
    }

    @Test
    public void testCRUDUnauthorized() throws Exception {
        mvc.perform(post("/api/v1/journal/create")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
