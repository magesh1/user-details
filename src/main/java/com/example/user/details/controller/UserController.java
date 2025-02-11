package com.example.user.details.controller;

import com.example.user.details.model.UserDetails;
import com.example.user.details.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDetails.class))}),
            @ApiResponse(responseCode = "409", description = "email already exists", content = @Content),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content)
    })
    @Tag(name = "post", description = "create user api")
    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody UserDetails userDetails) {
        userService.createUser(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body("message: success");
    }

    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "user not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content)
    })
    @Tag(name = "get", description = "get user details where you can sort the data or get response by pagination")
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
