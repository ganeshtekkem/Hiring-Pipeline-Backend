package com.ganesh.hiringpipeline.controller;

import com.ganesh.hiringpipeline.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import com.ganesh.hiringpipeline.dto.CreateUserRequest;
import com.ganesh.hiringpipeline.dto.UserResponse;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping
    public UserResponse createUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping
    public String getUsers(Authentication authentication) {
        return "Authenticated user: " + authentication.getName();
    }

}
