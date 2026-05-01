package com.Capstone.InterviewTracking.service.impl;

import com.Capstone.InterviewTracking.dto.AuthRequest;
import com.Capstone.InterviewTracking.dto.AuthResponse;
import com.Capstone.InterviewTracking.dto.SignupRequest;
import com.Capstone.InterviewTracking.entity.User;
import com.Capstone.InterviewTracking.enums.RoleType;
import com.Capstone.InterviewTracking.exception.EmailAlreadyRegisteredException;
import com.Capstone.InterviewTracking.exception.InvalidCredentialsException;
import com.Capstone.InterviewTracking.exception.UserNotFoundException;
import com.Capstone.InterviewTracking.mapper.UserMapper;
import com.Capstone.InterviewTracking.repository.UserRepository;
import com.Capstone.InterviewTracking.security.JwtUtil;
import com.Capstone.InterviewTracking.service.AuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;



@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
     
   private final EmailServiceImpl emailService;

      public AuthServiceImpl(UserRepository userRepository,
                           JwtUtil jwtUtil,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper,
                           EmailServiceImpl emailService) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.emailService = emailService; 
    }
  // register without password, send verification mail with token
    @Override
   public void register(SignupRequest request) {

       String email = request.getEmail().trim().toLowerCase();
       String name = request.getFullName().trim();
        Optional<User> existingUser = userRepository.findByEmail(email);
       
        if (existingUser.isPresent()) {
            User user = existingUser.get();

            if (user.isVerified()) {
                LOGGER.warn("Registration failed because email already exists: {}", email);
                throw new EmailAlreadyRegisteredException("Email already registered");
            }

            String verificationToken = UUID.randomUUID().toString();
            user.setVerificationToken(verificationToken);
            user.setTokenExpiry(LocalDateTime.now().plusMinutes(15));

            userRepository.save(user);

            LOGGER.info("User registered, verification email sent: {}", email);

            emailService.sendVerificationMail(email, name, verificationToken);

            return;
        }
        
    User user = userMapper.toUserForSignup(email, RoleType.CANDIDATE);

    String verificationToken = UUID.randomUUID().toString();

    user.setVerificationToken(verificationToken);
    user.setVerified(false);
    user.setTokenExpiry(LocalDateTime.now().plusMinutes(15));

    userRepository.save(user);

    LOGGER.info("User registered, verification email sent: {}", email);

    emailService.sendVerificationMail(email, name, verificationToken);
    
    }

    
    // set password and verify user
     public void setPassword(String token, String password) {

        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> {
                    LOGGER.warn("Invalid verification token: {}", token);
                    return new UserNotFoundException("Invalid or expired link");
                });

        if (user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            LOGGER.warn("Token expired for email: {}", user.getEmail());
            throw new RuntimeException("Link expired");
        }

        user.setPassword(passwordEncoder.encode(password));

        user.setVerified(true);

        user.setVerificationToken(null);
        user.setTokenExpiry(null);

        userRepository.save(user);

        LOGGER.info("User verified and password set: {}", user.getEmail());
    }
    @Override
    public AuthResponse login(AuthRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    LOGGER.warn("Login failed because user was not found: {}", email);
                    return new UserNotFoundException("User not found");
                });

                  if (!user.isVerified()) {
            LOGGER.warn("Login attempt before verification: {}", email);
            throw new InvalidCredentialsException("Please verify your email first");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            LOGGER.warn("Login failed because password was invalid for email: {}", email);
            throw new InvalidCredentialsException("Invalid password");
        }

        LOGGER.info("User logged in successfully: {}", email);
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new AuthResponse(token, user.getEmail(), user.getRole().name());
    }
}
