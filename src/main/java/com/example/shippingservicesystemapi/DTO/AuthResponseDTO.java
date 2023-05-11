package com.example.shippingservicesystemapi.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthResponseDTO {
    private String tokenType = "Bearer";
    private final String accessToken;
}
