package com.perception.backend.services;

import com.perception.backend.dtos.LoginRequestDTO;
import com.perception.backend.dtos.LoginResponseDTO;
import com.perception.backend.models.User;
import com.perception.backend.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public LoginResponseDTO generateLoginResponse(User user, String accessToken, String refreshToken) {
        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .role(user.getRole())
                .fullName(user.getFullName())
                .build();
    }

    public User register(User user) {
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                // You should generate JWT tokens here and return them
                String accessToken = "dummy-access-token"; // Replace with real token generation
                String refreshToken = "dummy-refresh-token"; // Replace with real token generation
                return generateLoginResponse(user, accessToken, refreshToken);
            }
        }
        throw new RuntimeException("Invalid credentials");
    }
}
