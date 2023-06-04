package com.example.shippingservicesystemapi.adapter;

import com.example.shippingservicesystemapi.enumeration.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

@Convert
public class UserRoleAdapter implements AttributeConverter<UserRole, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserRole userRole) {
        return userRole.getCode();
    }

    @Override
    public UserRole convertToEntityAttribute(Integer code) {
        return UserRole.getByCode(code);
    }
}
