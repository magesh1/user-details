package com.example.user.details.service;

import com.example.user.details.model.UserDetails;
import com.example.user.details.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserServiceTest {
    @Mock
    private UserRepo userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser() {
        // Create a sample user
        UserDetails user = new UserDetails();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setMobile("1234567890");

        // Call service method
        userService.createUser(user);

        // Verify save method was called once
        verify(userRepository, times(1)).save(user);
    }
}
