package com.dz.postgrescrud.controller;

import com.dz.postgrescrud.auth.AuthenticationService;
import com.dz.postgrescrud.auth.RegisterRequest;
import com.dz.postgrescrud.auth.user.User;
import com.dz.postgrescrud.configuration.JwtAuthenticationFilter;
import com.dz.postgrescrud.configuration.JwtService;
import com.dz.postgrescrud.domain.Journal;
import com.dz.postgrescrud.service.JournalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.dz.postgrescrud.auth.user.Role.MANAGER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JournalControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private AuthenticationService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JournalService mockJournalService;

    @InjectMocks
    private JournalController journalController;

    @Autowired
    private WebApplicationContext context;

    public JournalControllerTest() {
    }

    public String createToken(User user) {
        return jwtService.generateToken(user);
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(journalController)
                .addFilters(jwtAuthenticationFilter)
                .build();
    }

    @Test
    public void testCreateJournal() throws Exception {
        // Register a new test user
        var testManager = RegisterRequest.builder()
                .firstname("TestFirstname")
                .lastname("TestLastname")
                .email("test@mail.com")
                .password("password")
                .role(MANAGER)
                .build();
        // Obtain the bearer token aka access token (JWT)
        String token = service.register(testManager).getAccessToken();

        // Journal res from mocked service
        Journal mockJournalRes = new Journal();
        mockJournalRes.setJournalEntry("Test Journal Entry");

        // Mock the behavior of the service
        when(mockJournalService.createJournal(any(String.class))).thenReturn(new Journal());

        mockMvc.perform(post("/api/v1/journal/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content("Test Journal Entry"))
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("Test Journal Entry")));
    }

    @Test
    public void testCRUDUnauthorizedNoHeader() throws Exception {
        mockMvc.perform(post("/api/v1/journal/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("Test Journal Entry"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testCRUDUnauthorizedInvalidUser() throws Exception {
        // This user is created at startup in PostgrescrudApplication.java
        User invalidUser = User.builder()
                .firstname("invalid")
                .lastname("invalid")
                .email("invalid@mail.com")
                .password("invalid")
                .role(MANAGER)
                .build();

        String invalidToken = createToken(invalidUser);

        mockMvc.perform(post("/api/v1/journal/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + invalidToken)
                        .content("Test Journal Entry"))
                .andExpect(status().is4xxClientError());
    }
}
