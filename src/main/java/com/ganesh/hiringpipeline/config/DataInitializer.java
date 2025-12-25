package com.ganesh.hiringpipeline.config;

import com.ganesh.hiringpipeline.repository.RoleRepository;
import com.ganesh.hiringpipeline.role.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("ADMIN").isEmpty()) {
                roleRepository.save(new Role("ADMIN"));
            }
            if (roleRepository.findByName("RECRUITER").isEmpty()) {
                roleRepository.save(new Role("RECRUITER"));
            }
        };
    }
}
