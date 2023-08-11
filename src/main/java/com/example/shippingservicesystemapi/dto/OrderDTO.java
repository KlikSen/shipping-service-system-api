package com.example.shippingservicesystemapi.dto;

import com.example.shippingservicesystemapi.enumeration.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    @PositiveOrZero
    @NotNull
    private Long shopId;
    @PositiveOrZero
    @NotNull
    private Long customerId;
    @PositiveOrZero
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long courierId;
    @NotNull
    private String locality;
    @NotNull
    private String street;
    @NotNull
    @Positive
    private Integer number;
    @Valid
    @NotNull
    @Size(min = 1)
    private List<OrderedProductDTO> orderedProductDTOList = new ArrayList<>();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double totalAmount;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OrderStatus orderStatus;
}
