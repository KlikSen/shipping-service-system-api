package com.example.shippingservicesystemapi.service;

import com.example.shippingservicesystemapi.dto.ConfirmationDTO;
import com.example.shippingservicesystemapi.entity.User;

public interface ConfirmationService {
    ConfirmationDTO create(User user);
    void confirm(String token);
}
