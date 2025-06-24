package com.perception.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String email;
    private String role;
    private String fullName;

    public static LoginResponseDTO builder() {
        return new LoginResponseDTO();
    }
    public static LoginResponseDTO build() {
        return new LoginResponseDTO();
    }

    public LoginResponseDTO accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }
    public LoginResponseDTO refreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }
    public LoginResponseDTO email(String email) {
        this.email = email;
        return this;
    }
    public LoginResponseDTO role(String role) {
        this.role = role;
        return this;
    }
    public LoginResponseDTO fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
