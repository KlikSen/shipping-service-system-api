package com.example.shippingservicesystemapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @PositiveOrZero
    @NotNull
    private Double price;
    @PositiveOrZero
    @NotNull
    private int quantity;
    private Long shopId;
}
