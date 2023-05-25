package com.example.shippingservicesystemapi.dto;

import com.example.shippingservicesystemapi.enumeration.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email(regexp = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$") //also includes @NotNull
    private String email;
    @NotEmpty
    private String password;
    @NotNull
    private UserRole userRole;
}
