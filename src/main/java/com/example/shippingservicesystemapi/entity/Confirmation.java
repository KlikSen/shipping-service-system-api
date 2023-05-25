package com.example.shippingservicesystemapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Confirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token")
    private String token;
    @Column(name = "created")
    private LocalDateTime created;
    @Column(name = "confirmed")
    private LocalDateTime confirmed;
    @Column(name = "expired")
    private LocalDateTime expired;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
