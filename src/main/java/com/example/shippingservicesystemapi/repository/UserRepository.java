package com.example.shippingservicesystemapi.repository;

import com.example.shippingservicesystemapi.entity.User;
import jakarta.transaction.Transactional;
import org.mapstruct.Named;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(final String email);
    boolean existsByEmail(final String email);

    @Modifying
    @Query("update User "+
        "set emailVerification = :emailVerification " +
        "where id = :id")
    void updateEmailVerificationById(Long id, boolean emailVerification);
    @Modifying
    @Query("update User as user " +
            "set user.firstName = :firstName, " +
            "user.lastName = :lastName " +
            "where user.id = :id")
    void update(Long id, String firstName, String lastName);
}
