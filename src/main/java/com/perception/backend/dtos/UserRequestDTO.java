package com.perception.backend.dtos;

import com.perception.backend.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "Full name is required")
    private String fullName;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;
    private String status;
    private String role;

    public User toUser() {
        User user = new User();
        user.setFullName(this.fullName);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setMobileNumber(this.mobileNumber);
        user.setUsername(this.email);
        user.setRole(this.role != null ? this.role : ("admin@example.com".equalsIgnoreCase(this.email) ? "ADMIN" : "USER"));
        user.setStatus(this.status != null ? this.status : "ACTIVE");
        return user;
    }
}


