package com.example.shippingservicesystemapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "ordered_product")
@Data
@ToString(exclude = "order")
public class OrderedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Order order;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "current_price")
    private Double currentPrice;
}
