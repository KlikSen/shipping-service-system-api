package com.example.shippingservicesystemapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopDTO {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String locality;
    @NotNull
    private String street;
    @NotNull
    private Integer number;
    private Long shopOwnerId;
    @Valid
    @NotNull
    @Size(min = 1, max = 7)
    private List<WorkDayDTO> workDayDTOList;
}
