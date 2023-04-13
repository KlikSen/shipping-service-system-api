package com.example.shippingservicesystemapi.entity;

import com.example.shippingservicesystemapi.enumeration.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="user_role")
    private UserRole userRole;

    @Column(name = "register_time")
    private LocalDateTime registerTime;

    @Column(name = "email_verification")
    private boolean emailVerification;
}
