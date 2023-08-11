package com.example.shippingservicesystemapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private Double price;
    @ManyToOne
    private Shop shop;
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private Storage storage;

    public void setUpStorage() { //it has to be used after mapping
        storage.setProduct(this);
    }
}
