package com.ganesh.hiringpipeline.dto;

public class CreateUserRequest {
    private String name;
    private String email;
    private String password;
    private String role;

    public String getName() {
    return name;
    }

    public String getRole(){
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }
}
