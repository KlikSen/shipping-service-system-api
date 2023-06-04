package com.example.shippingservicesystemapi.repository;

import com.example.shippingservicesystemapi.entity.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JwtTokenRepository extends JpaRepository<Jwt, Long> {
    Jwt findByToken(final String token);

    void deleteByToken(final String token);

    @Modifying
    @Query("DELETE FROM Jwt WHERE user.id = :userId")
    void deleteJwtByUserId(final Long userId);

    @Query(value = "SELECT * FROM jwt_token WHERE user_id = :user_id", nativeQuery = true)
    List<Jwt> findByUser(final long user_id);
}
