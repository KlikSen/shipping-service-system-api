package com.example.shippingservicesystemapi.enumeration;

import java.util.Arrays;

public enum UserRole {
    COURIER(0),
    OWNER(1),
    BUYER(2);

    private Integer code;

    UserRole(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static UserRole getByCode(final Integer code) {
        return Arrays.stream(UserRole.values()).filter(o -> o.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("UserRole not found by code"));
    }
}
