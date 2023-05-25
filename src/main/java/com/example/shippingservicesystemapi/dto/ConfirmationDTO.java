package com.example.shippingservicesystemapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmationDTO {
    private Long id;
    private String token;
    private LocalDateTime created;
    private LocalDateTime confirmed;
    private LocalDateTime expired;
    private UserDTO userDTO;
}
