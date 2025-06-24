    package com.perception.backend.services;

    import com.perception.backend.dtos.LoginRequestDTO;
    import com.perception.backend.dtos.LoginResponseDTO;
    import com.perception.backend.models.User;
    import com.perception.backend.repositories.UserRepository;
    import com.perception.backend.security.JwtTokenProvider;
    // import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;

    @Service
    public class UserService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtTokenProvider jwtTokenProvider;

        public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
            this.jwtTokenProvider = jwtTokenProvider;
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
                String accessToken = jwtTokenProvider.generateToken(user.getEmail());
                String refreshToken = jwtTokenProvider.generateToken(user.getEmail());
                return generateLoginResponse(user, accessToken, refreshToken);
            }
        }
        throw new RuntimeException("Invalid credentials");
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User updateUserRole(Long id, String role) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(role);
        return userRepository.save(user);
    }

    public User updateUserStatus(Long id, String status) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(status);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    }
