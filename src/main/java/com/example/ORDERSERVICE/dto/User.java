package com.example.ORDERSERVICE.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class User {
    UUID id;
    String userName;
    String password;
    String role;
}
