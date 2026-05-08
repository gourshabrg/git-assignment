package com.Capstone.InterviewTracking.config;

import com.Capstone.InterviewTracking.entity.User;
import com.Capstone.InterviewTracking.enums.RoleType;
import com.Capstone.InterviewTracking.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedData(UserRepository userRepository,
                                      PasswordEncoder passwordEncoder) {

        return args -> {
            String hrEmail = "vingo.food.service@gmail.com";
            if (userRepository.findByEmail(hrEmail).isEmpty()) {

                User hr = new User();
                hr.setEmail(hrEmail);
                hr.setPassword(passwordEncoder.encode("123456"));

                hr.setRole(RoleType.HR);

                hr.setVerified(true);

                userRepository.save(hr);

                System.out.println(" gm:vingo.food.service@gmail.com , password:123456 HR user created successfully");
            } else {
                System.out.println("  gm:vingo.food.service@gmail.com , password:123456 HR already exists");
            }
        };
    }
}
