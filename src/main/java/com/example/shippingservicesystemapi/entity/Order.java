package com.example.shippingservicesystemapi.entity;

import com.example.shippingservicesystemapi.adapter.OrderStatusConverter;
import com.example.shippingservicesystemapi.enumeration.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;
    @ManyToOne
    @JoinColumn(name = "courier_id")
    private User courier;
    @Column(name = "locality")
    private String locality;
    @Column(name = "street")
    private String street;
    @Column(name = "number")
    private Integer number;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderedProduct> orderedProductList = new ArrayList<>();
    @Column(name = "total_amount")
    private double totalAmount;
    @Column(name = "status")
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus orderStatus;

    public void setUpOrderedProductList() {
        orderedProductList.forEach(o -> o.setOrder(this));
    }
}