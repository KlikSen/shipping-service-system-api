package com.example.shippingservicesystemapi.mapper;

import com.example.shippingservicesystemapi.dto.ConfirmationDTO;
import com.example.shippingservicesystemapi.entity.Confirmation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConfirmationMapper {
    @Mapping(target="user", source="confirmationDTO.userDTO")
    Confirmation toEntity (ConfirmationDTO confirmationDTO);
    @Mapping(target="userDTO", source="confirmation.user")
    ConfirmationDTO toDTO (Confirmation confirmation);
}
