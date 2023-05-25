package com.example.shippingservicesystemapi.service;

import com.example.shippingservicesystemapi.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO create(UserDTO userDTO);
    UserDTO read(Long id);
    UserDTO update(UserDTO userDTO);
    void delete(Long id);
    List<UserDTO> readAll();
    void updateEmailVerificationById(Long id, boolean emailVerification);
    UserDTO readByEmail(String email);
}
