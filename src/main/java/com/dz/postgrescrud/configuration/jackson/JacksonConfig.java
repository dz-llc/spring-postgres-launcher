package com.dz.postgrescrud.configuration.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Adding this configuration will enable the serialization and deserialization of Java 8 date/time types such as Instant in your application.
@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> {
            ObjectMapper objectMapper = builder.build();
            objectMapper.registerModule(new JavaTimeModule());
        };
    }
}