package com.example.shippingservicesystemapi.service;

import com.example.shippingservicesystemapi.dto.LoginDTO;
import com.example.shippingservicesystemapi.dto.RegisterDTO;

public interface AuthService {
    void register(RegisterDTO registerDTO);
    Object login(LoginDTO loginDTO); //return jwt token
}
