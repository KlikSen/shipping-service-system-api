package com.example.shippingservicesystemapi.repository;

import com.example.shippingservicesystemapi.entity.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface JwtTokenRepository extends JpaRepository<Jwt, Long> {
    Jwt findByToken(final String token);

    void deleteByToken(final String token);
}
