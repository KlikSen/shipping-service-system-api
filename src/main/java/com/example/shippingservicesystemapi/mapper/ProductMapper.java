package com.example.shippingservicesystemapi.mapper;

import com.example.shippingservicesystemapi.dto.ProductDTO;
import com.example.shippingservicesystemapi.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring") //the component model is used in order to autowire a bean leveraging annotation
public interface ProductMapper {
    @Mappings(value = {
            @Mapping(target = "storage.quantity", source = "quantity"),
            @Mapping(target = "shop.id", source = "shopId"), //in shop entity I need to put 'this' into setProduct(),
            @Mapping(target = "price", source = "price")
    })
    Product toEntity(ProductDTO productDTO);
    @Mappings(value = {
            @Mapping(target = "quantity", source = "storage.quantity"),
            @Mapping(target = "shopId", source = "shop.id"),
            @Mapping(target = "price", source = "price")
    })
    ProductDTO toDTO(Product product);
}
