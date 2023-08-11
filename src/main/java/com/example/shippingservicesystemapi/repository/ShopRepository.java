package com.example.shippingservicesystemapi.repository;

import com.example.shippingservicesystemapi.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    @Modifying
    @Query("update Shop as a " +
            "set a.name = :name, " +
            "a.locality = :locality, " +
            "a.street = :street, " +
            "a.number = :number " +
            "where a.id = :id")
    void update(long id,
                String name,
                String locality,
                String street,
                Integer number);

    @Query("select shop from Shop as shop " +
            "inner join fetch shop.workDayList as workDay " +
            "inner join fetch shop.shopOwner " +
            "where (:workingNow = false or (:time between workDay.startTime and workDay.endTime and workDay.dayOfWeek = :dayOfWeek))")
    List<Shop> findAllByCriteria(boolean workingNow, LocalTime time, DayOfWeek dayOfWeek);
}