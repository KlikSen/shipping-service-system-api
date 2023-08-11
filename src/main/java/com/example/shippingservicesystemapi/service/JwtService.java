package com.example.shippingservicesystemapi.service;

import com.example.shippingservicesystemapi.dto.UserDTO;
import com.example.shippingservicesystemapi.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface JwtService {
    String generateJwt (User user);
    boolean isJwtValid(String token);
    void deleteJwt(String token);
    String getUsername(String token);
    String getJwtFromRequest(HttpServletRequest httpServletRequest);
    String getPrincipalFromRequest(HttpServletRequest httpServletRequest);
}
