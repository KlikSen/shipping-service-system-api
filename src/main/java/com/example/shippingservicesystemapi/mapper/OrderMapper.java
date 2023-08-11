package com.example.shippingservicesystemapi.mapper;

import com.example.shippingservicesystemapi.dto.OrderDTO;
import com.example.shippingservicesystemapi.dto.OrderedProductDTO;
import com.example.shippingservicesystemapi.entity.Order;
import com.example.shippingservicesystemapi.entity.OrderedProduct;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mappings(value = {
            @Mapping(target = "shop.id", source = "shopId"),
            @Mapping(target = "customer.id", source = "customerId"),
            @Mapping(target = "courier.id", source = "courierId"),
            @Mapping(target = "orderedProductList", source = "orderedProductDTOList", qualifiedByName = "orderedProductToEntity"),
            @Mapping(target = "orderStatus", source = "orderStatus")
    })
    Order toEntity(OrderDTO orderDTO);
    @Mappings(value = {
            @Mapping(target = "shopId", source = "shop.id"),
            @Mapping(target = "customerId", source = "customer.id"),
            @Mapping(target = "courierId", source = "courier.id"),
            @Mapping(target = "orderedProductDTOList", source = "orderedProductList", qualifiedByName = "orderedProductToDTO"),
            @Mapping(target = "orderStatus", source = "orderStatus")
    })
    OrderDTO toDTO(Order order);

    @Named("orderedProductToDTO")
    default List<OrderedProductDTO> toDTO(List<OrderedProduct> orderedProductList) {
        OrderedProductMapper orderedProductMapper = Mappers.getMapper(OrderedProductMapper.class);

        return orderedProductList.stream()
                .map(orderedProductMapper::toDTO)
                .toList();
    }

    @Named("orderedProductToEntity")
    default List<OrderedProduct> toEntity(List<OrderedProductDTO> orderedProductList) {
        OrderedProductMapper orderedProductMapper = Mappers.getMapper(OrderedProductMapper.class);

        return orderedProductList.stream()
                .map(orderedProductMapper::toEntity)
                .toList();
    }
}
