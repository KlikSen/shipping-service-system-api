package com.example.shippingservicesystemapi.repository;

import com.example.shippingservicesystemapi.entity.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {
    List<WorkDay> findAllByShopId(long shopId);
}
