package com.example.shippingservicesystemapi.repository;

import com.example.shippingservicesystemapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Query(value = "update product as a inner join storage as b " +
            "set a.name = :name, " +
            "a.description = :description, " +
            "a.price = :price, " +
            "b.quantity = :quantity " +
            "where a.id = :id", nativeQuery = true)
    void update(Long id, String name, String description, double price, int quantity);

    @Query("select product from Product as product " +
            "inner join fetch product.shop.workDayList as workDay " +
            "inner join fetch product.storage as storage " +
            "where (:shopId is null or product.shop.id = :shopId) and " +
            "(:available = false or storage.quantity>0) and " +
            "(:possible = false or (:time between workDay.startTime and workDay.endTime and workDay.dayOfWeek = :dayOfWeek))"
    )
    List<Product> findAllByCriteria(
            @Param("shopId") Long shopId,
            @Param("available") boolean availableQuantity,
            @Param("possible") boolean possibleToOrderNow,
            @Param("time") LocalTime time,
            @Param("dayOfWeek") DayOfWeek dayOfWeek
    );
}
