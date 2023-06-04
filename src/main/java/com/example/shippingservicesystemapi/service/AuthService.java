package com.example.shippingservicesystemapi.service;

import com.example.shippingservicesystemapi.dto.JwtDTO;
import com.example.shippingservicesystemapi.dto.LoginDTO;
import com.example.shippingservicesystemapi.dto.RegisterDTO;
import jakarta.servlet.http.HttpServletRequest;


public interface AuthService {
    void register(RegisterDTO registerDTO);
    JwtDTO login(LoginDTO loginDTO);
    void logout (HttpServletRequest httpServletRequest);
}
