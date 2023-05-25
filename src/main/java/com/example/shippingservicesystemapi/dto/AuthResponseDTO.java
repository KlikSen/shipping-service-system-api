package com.example.shippingservicesystemapi.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
public class AuthResponseDTO {
    private String tokenType = "Bearer";
    private final String accessToken;
}
