package com.example.shippingservicesystemapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "storage")
@Getter
@ToString(exclude = "product")
@Setter
@NoArgsConstructor
public class Storage {
    @Id
    private Long id;
    @Column(name = "quantity")
    private int quantity;
    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    public Storage(int quantity) {
        this.quantity = quantity;
    }
}
