package com.example.shippingservicesystemapi.service;

import com.example.shippingservicesystemapi.dto.ConfirmationDTO;
import com.example.shippingservicesystemapi.dto.UserDTO;

public interface ConfirmationService {
    ConfirmationDTO create(UserDTO userDTO);
    void confirm(String token);
}
