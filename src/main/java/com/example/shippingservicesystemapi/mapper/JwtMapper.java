package com.example.shippingservicesystemapi.mapper;

import com.example.shippingservicesystemapi.dto.JwtDTO;
import com.example.shippingservicesystemapi.entity.Jwt;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtMapper {
    Jwt toEntity (JwtDTO jwtDTO);
    JwtDTO toDTO (Jwt jwt);
}
