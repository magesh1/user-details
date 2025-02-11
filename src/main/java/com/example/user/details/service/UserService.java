package com.example.user.details.service;

import com.example.user.details.model.UserDetails;
import com.example.user.details.repository.UserRepo;
import com.example.user.details.utils.exception.UserAlreadyExists;
import com.example.user.details.utils.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void createUser(UserDetails userDetails) {
        if (userRepo.existsByEmail(userDetails.getEmail())) {
            throw new UserAlreadyExists("email already exists");
        }
        try {
            userRepo.save(userDetails);
        } catch (Exception e) {
            logger.error("\n err creating user: {}", e.getMessage());
            throw e;
        }
    }

    public List<UserDetails> listUsers(Pageable pageable) {
        try {
            Page<UserDetails> result = userRepo.findAll(pageable);
            List<UserDetails> res = result.getContent();
            return res;
        } catch (Exception e) {
            logger.error("\n err getting user details: {}", e.getMessage());
            throw e;
        }
    }

    public UserDetails singleUser(Optional<Long> id) {
        Optional<UserDetails> result = userRepo.findById(id.get());
        UserDetails userDetails;

        if (!result.isPresent()) {
            throw new UserNotFoundException("user not found with id: " + id.get());
        }

        userDetails = result.get();

        return userDetails;
    }

}
