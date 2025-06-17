package com.realtime.auction.user_service.controller;

import com.realtime.auction.user_service.model.UserProfile;
import com.realtime.auction.user_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

//    @Test
//    void shouldReturnUserRoles() throws Exception {
//        UUID userId = UUID.randomUUID();
//        userRepository.save(
//                UserProfile.builder()
//                        .id(userId)
//                        .name("Test User")
//                        .balance(BigDecimal.valueOf(1000))
//                        .roles(Set.of("ROLE_ADMIN"))
//                        .build());
//
//        mockMvc.perform(get("/api/users/{id}/roles", userId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", contains("ROLE_ADMIN")));
//    }
}
