package com.perception.backend.dtos;

import com.perception.backend.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String mobileNumber;

    public static UserResponseDTO fromUser(User user) {
        return new UserResponseDTO(
            user.getId(),
            user.getFullName(),
            user.getEmail(),
            user.getMobileNumber()
        );
    }
}
