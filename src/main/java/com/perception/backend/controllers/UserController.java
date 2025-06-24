package com.perception.backend.controllers;

import com.perception.backend.dtos.LoginRequestDTO;
import com.perception.backend.dtos.LoginResponseDTO;
import com.perception.backend.dtos.UserRequestDTO;
import com.perception.backend.dtos.UserResponseDTO;
import com.perception.backend.models.User;
import com.perception.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO request) {
        User user = userService.register(request.toUser());
        return ResponseEntity.ok(UserResponseDTO.fromUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
