package com.example.shippingservicesystemapi.repository;

import com.example.shippingservicesystemapi.entity.Order;
import com.example.shippingservicesystemapi.enumeration.OrderStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "select orders from Order as orders " +
            "left join fetch orders.courier " +
            "inner join fetch orders.orderedProductList as orderedProduct " +
            "inner join fetch orderedProduct.product as product " +
            "inner join fetch product.storage " +
            "inner join fetch product.shop.shopOwner " +
            "where orders.customer.id = :id"
    )
    List<Order> findByCustomerId(Long id);

    List<Order> findByShopId(long shopId);


    List<Order> findAllByOrderStatus(OrderStatus orderStatus);
}