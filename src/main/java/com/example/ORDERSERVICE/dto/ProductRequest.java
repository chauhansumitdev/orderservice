package com.example.ORDERSERVICE.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductRequest {
    UUID id;
    Integer quantity;
}
