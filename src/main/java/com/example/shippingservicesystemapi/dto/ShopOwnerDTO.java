package com.example.shippingservicesystemapi.dto;

import com.example.shippingservicesystemapi.enumeration.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopOwnerDTO { //now it is used only for signing up
    //creating a user
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email(regexp = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$")
    private String email;
    @NotEmpty
    private String password;
    @JsonProperty(value = "userRole", access = JsonProperty.Access.READ_ONLY)
    private UserRole userRole;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "registerTime", access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime registerTime;
    @JsonProperty(value = "emailVerification", access = JsonProperty.Access.READ_ONLY)
    private boolean emailVerification;
    //creating shop
    private Long shopId;
    @NotNull
    private String name;
    @NotNull
    private String locality;
    @NotNull
    private String street;
    @NotNull
    private Integer number;
    @Valid
    @NotNull
    @Size(min = 1, max = 7)
    private List<WorkDayDTO> workDayDTOList;
}
