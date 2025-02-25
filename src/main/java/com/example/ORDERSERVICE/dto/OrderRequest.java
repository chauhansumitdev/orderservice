package com.example.ORDERSERVICE.dto;

import lombok.*;

import java.util.List;

@Data
public class OrderRequest {
    private List<ProductRequest> products;
}
