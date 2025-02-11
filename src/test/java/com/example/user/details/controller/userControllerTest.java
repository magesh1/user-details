package com.example.user.details.controller;

import com.example.user.details.model.UserDetails;
import com.example.user.details.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class userControllerTest {
    private final MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public userControllerTest() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testCreateUser() throws Exception {
        // Create a sample user
        UserDetails user = new UserDetails();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setMobile("1234567890");

        // Convert user object to JSON
        String userJson = objectMapper.writeValueAsString(user);

        // Perform HTTP POST request
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());

        // Verify service method was called
        verify(userService, times(1)).createUser(any(UserDetails.class));
    }


}
