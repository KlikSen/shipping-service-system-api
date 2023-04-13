package com.example.shippingservicesystemapi.service;

import com.example.shippingservicesystemapi.DTO.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO create(UserDTO userDTO);
    UserDTO read(Long id);
    UserDTO update(UserDTO userDTO);
    void delete(Long id);
    List<UserDTO> readAll();
}
