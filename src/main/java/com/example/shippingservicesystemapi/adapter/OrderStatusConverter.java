package com.example.shippingservicesystemapi.adapter;

import com.example.shippingservicesystemapi.enumeration.OrderStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class OrderStatusConverter implements AttributeConverter<OrderStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(OrderStatus orderStatus) {
        return orderStatus.getCode();
    }

    @Override
    public OrderStatus convertToEntityAttribute(Integer code) {
        return OrderStatus.getByCode(code);
    }
}
