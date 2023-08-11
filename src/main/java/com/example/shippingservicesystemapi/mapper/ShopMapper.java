package com.example.shippingservicesystemapi.mapper;

import com.example.shippingservicesystemapi.dto.ShopDTO;
import com.example.shippingservicesystemapi.dto.ShopOwnerDTO;
import com.example.shippingservicesystemapi.entity.Shop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ShopMapper {
    @Mapping(target = "shopOwnerId", source = "shopOwner.id")
    @Mapping(target = "workDayDTOList", source = "workDayList")
    ShopDTO toDTO(Shop shop);
    @Mapping(target = "shopOwner.id", source = "shopOwnerId")
    @Mapping(target = "workDayList", source = "workDayDTOList")
    Shop toEntity(ShopDTO shopDTO);
    @Mapping(target = "id", source = "shopId")
    @Mapping(target = "shopOwnerId", source = "id")
    ShopDTO toDTO(ShopOwnerDTO shopOwnerDTO);
}
