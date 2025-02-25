package com.example.ORDERSERVICE.repository;

import com.example.ORDERSERVICE.entity.Order;
import com.example.ORDERSERVICE.entity.OrderProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderProductRepository extends JpaRepository<OrderProduct, UUID> {
    Page<OrderProduct> findByOrderId(UUID orderId, Pageable pageable);
}
