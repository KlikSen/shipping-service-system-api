package com.example.shippingservicesystemapi.service;

import com.example.shippingservicesystemapi.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface JwtService {
    String generateJwt (UserDTO userDTO);
    boolean isJwtValid(String token);
    void deleteJwt(String token);
    void deleteJwtByUserId(Long userId);
    List<String> getJwtByUserId(Long userId);
    UserDTO getUser(String token);
    String getUsername(String token);
    String getJwtFromRequest(HttpServletRequest httpServletRequest);
    String getPrincipalFromRequest(HttpServletRequest httpServletRequest);
}
