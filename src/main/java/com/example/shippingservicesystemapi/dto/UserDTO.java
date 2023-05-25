package com.example.shippingservicesystemapi.dto;

import com.example.shippingservicesystemapi.enumeration.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{
    private Long id;
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "registerTime", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime registerTime;
    @JsonProperty(value = "emailVerification", access = JsonProperty.Access.READ_ONLY)
    private boolean emailVerification;
}
