package com.example.shippingservicesystemapi.mapper;

import com.example.shippingservicesystemapi.dto.OrderedProductDTO;
import com.example.shippingservicesystemapi.entity.OrderedProduct;
import jakarta.persistence.Table;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderedProductMapper {
    @Mappings(value = {
            @Mapping(target = "product.id", source = "productId"),
            @Mapping(target = "product.name", source = "name"),
            @Mapping(target = "product.description", source = "description"),
            @Mapping(target = "currentPrice", source = "currentPrice"),
            @Mapping(target = "quantity", source = "quantity")
    })
    OrderedProduct toEntity(OrderedProductDTO orderedProductDTO);
    @Mappings(value = {
            @Mapping(target = "productId", source = "product.id"),
            @Mapping(target = "name", source = "product.name"),
            @Mapping(target = "description", source = "product.description"),
            @Mapping(target = "currentPrice", source = "currentPrice"),
            @Mapping(target = "quantity", source = "quantity")
    })
    OrderedProductDTO toDTO(OrderedProduct orderedProduct);
}
