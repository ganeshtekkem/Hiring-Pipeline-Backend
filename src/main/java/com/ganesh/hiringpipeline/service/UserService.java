package com.ganesh.hiringpipeline.service;
import org.springframework.stereotype.Service;

import com.ganesh.hiringpipeline.dto.CreateUserRequest;
import com.ganesh.hiringpipeline.repository.*;
import com.ganesh.hiringpipeline.role.Role;
import com.ganesh.hiringpipeline.user.*;
import com.ganesh.hiringpipeline.exception.BadRequestException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    public UserService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User createUser(CreateUserRequest request){

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new BadRequestException("User with email " + request.getEmail() + " already exists.");
        }

        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new BadRequestException("Role " + request.getRole() + " not found."));

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(role);

        return userRepository.save(user);

    }
}
