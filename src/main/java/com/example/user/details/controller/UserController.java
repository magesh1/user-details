package com.example.user.details.controller;

import com.example.user.details.model.UserDetails;
import com.example.user.details.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/v1")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody UserDetails userDetails) {
        userService.createUser(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body("message: success");
    }

    @GetMapping("/user")
    public ResponseEntity<?> listUser(@RequestParam(defaultValue = "createdAt") String sortBy,
                                      @RequestParam(defaultValue = "desc") String orderBy,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "5") int size,
                                      @RequestParam(value = "id", required = false) Optional<Long> id) {

        if (id.isPresent()) {
            UserDetails resp = userService.singleUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }

        Sort.Direction direction = orderBy.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        List<UserDetails> resp = userService.listUsers(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }


}
