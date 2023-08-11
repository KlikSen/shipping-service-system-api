package com.example.shippingservicesystemapi.enumeration;

import java.util.Arrays;

public enum OrderStatus {
    OPENED(0),
    IN_PROCESSING(1),
    READY_TO_DELIVER(2),
    DELIVERING(3),
    COMPLETED(4);

    private final Integer code;

    OrderStatus(int code) {
        this.code = code;
    }

    public static OrderStatus getByCode(final int code) {
        return Arrays.stream(OrderStatus.values()).filter(o -> o.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("OrderStatus not found by code"));
    }

    public Integer getCode() {
        return code;
    }
}
