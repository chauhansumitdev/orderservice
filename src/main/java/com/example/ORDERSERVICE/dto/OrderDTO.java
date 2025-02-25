package com.example.ORDERSERVICE.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private UUID id;
    private String orderNumber;
    private UUID userId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
