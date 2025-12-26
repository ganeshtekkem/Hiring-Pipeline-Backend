package com.ganesh.hiringpipeline.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.ganesh.hiringpipeline.dto.LoginRequest;
import com.ganesh.hiringpipeline.exception.BadRequestException;
import com.ganesh.hiringpipeline.repository.UserRepository;
import com.ganesh.hiringpipeline.security.JwtService;
import com.ganesh.hiringpipeline.user.User;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder encoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public String Login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> 
                    new BadRequestException("Invalid email or password"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }
        return jwtService.generateToken(user);

    }



    
}
