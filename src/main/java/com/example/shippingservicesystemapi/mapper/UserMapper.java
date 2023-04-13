package com.example.shippingservicesystemapi.mapper;

import com.example.shippingservicesystemapi.DTO.UserDTO;
import com.example.shippingservicesystemapi.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity (UserDTO userDTO);
    UserDTO toDTO (User user);
}
