package com.example.shippingservicesystemapi.service;

import com.example.shippingservicesystemapi.dto.JwtDTO;
import com.example.shippingservicesystemapi.dto.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;


public interface AuthService {
    JwtDTO login(LoginDTO loginDTO);
    void logout (HttpServletRequest httpServletRequest);
}
