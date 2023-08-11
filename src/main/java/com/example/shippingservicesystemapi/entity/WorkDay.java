package com.example.shippingservicesystemapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "work_day")
@AllArgsConstructor
@Data
@ToString(exclude = "shop")
@NoArgsConstructor
public class WorkDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "start_time")
    private LocalTime startTime;
    @JoinColumn(name = "end_time")
    private LocalTime endTime;
    @JoinColumn(name = "day_of_week")
    @Enumerated(value = EnumType.ORDINAL)
    private DayOfWeek dayOfWeek;
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
}
