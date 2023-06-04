package com.example.shippingservicesystemapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "jwt")
@Data
@Accessors(chain = true)
public class Jwt {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token")
    private String token;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
