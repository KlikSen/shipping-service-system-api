package com.example.shippingservicesystemapi.repository;

import com.example.shippingservicesystemapi.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StorageRepository extends JpaRepository<Storage, Long> {
}
