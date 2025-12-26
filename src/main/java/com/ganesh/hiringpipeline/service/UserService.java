package com.ganesh.hiringpipeline.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.ganesh.hiringpipeline.dto.CreateUserRequest;
import com.ganesh.hiringpipeline.dto.UserResponse;
import com.ganesh.hiringpipeline.repository.*;
import com.ganesh.hiringpipeline.role.Role;
import com.ganesh.hiringpipeline.user.*;
import com.ganesh.hiringpipeline.exception.BadRequestException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

   public UserResponse createUser(CreateUserRequest request) {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
        throw new BadRequestException(
                "User with email " + request.getEmail() + " already exists."
        );
    }

    Role role = roleRepository.findByName(request.getRole())
            .orElseThrow(() ->
                    new BadRequestException(
                            "Role " + request.getRole() + " not found."
                    )
            );
    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(role);
    User savedUser = userRepository.save(user);

    UserResponse response = new UserResponse();
    response.setId(savedUser.getId());
    response.setName(savedUser.getName());
    response.setEmail(savedUser.getEmail());
    response.setRole(savedUser.getRole().getName());

    return response;
}

}
