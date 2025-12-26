package com.ganesh.hiringpipeline.controller;

import org.springframework.web.bind.annotation.*;
import com.ganesh.hiringpipeline.dto.LoginRequest;
import com.ganesh.hiringpipeline.service.AuthService;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }
    
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){
        return authService.Login(request);
    }

}
