package com.example.shippingservicesystemapi.service;

import com.example.shippingservicesystemapi.dto.ShopOwnerDTO;
import com.example.shippingservicesystemapi.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO create(UserDTO userDTO);
    UserDTO read(Long id);
    UserDTO update(UserDTO userDTO);
    void updateEmail(long id, String email);
    void updatePassword(long id, String password);
    void delete(Long id);
    List<UserDTO> readAll();
    UserDTO readByEmail(String email);
    UserDTO createShopOwner(ShopOwnerDTO shopOwnerDTO);
}
