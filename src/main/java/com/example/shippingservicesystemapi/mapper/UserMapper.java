package com.example.shippingservicesystemapi.mapper;

import com.example.shippingservicesystemapi.dto.ShopOwnerDTO;
import com.example.shippingservicesystemapi.dto.UserDTO;
import com.example.shippingservicesystemapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity (UserDTO userDTO);
    UserDTO toDTO (User user);
    User toEntity(ShopOwnerDTO shopOwnerDTO);
}
