package com.example.shippingservicesystemapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shop")
@Data
@NoArgsConstructor
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "locality")
    private String locality;
    @Column(name = "street")
    private String street;
    @Column(name = "number")
    private Integer number;
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User shopOwner;
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkDay> workDayList = new ArrayList<>();

    public void setUpWorkDayList() {
        workDayList.forEach(o -> o.setShop(this));
    }
}
