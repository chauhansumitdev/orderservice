package com.example.ORDERSERVICE.repository;

import com.example.ORDERSERVICE.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findByUserId(UUID userId, Pageable pageable);
    boolean existsByUserId(UUID userId);
    boolean existsById(UUID orderid);
}
