package com.example.shippingservicesystemapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedProductDTO { //used only for displaying
    private Long id;
    @NotNull
    @Positive
    private long productId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String description;
    @NotNull
    @Positive
    private Integer quantity;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double currentPrice;
}
