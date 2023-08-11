package com.example.shippingservicesystemapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDayDTO {
    private Long id;
    @JsonFormat(pattern = "HH:mm")
    @NotNull
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm")
    @NotNull
    private LocalTime endTime;
    @NotNull
    private DayOfWeek dayOfWeek;
}
