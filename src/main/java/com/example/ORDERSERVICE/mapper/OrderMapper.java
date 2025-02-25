package com.example.ORDERSERVICE.mapper;

import com.example.ORDERSERVICE.dto.OrderDTO;
import com.example.ORDERSERVICE.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order order) {
        return new OrderDTO(order.getId(), order.getOrderNumber(), order.getUserId(), order.getStatus(), order.getCreatedAt(), order.getUpdatedAt());
    }

    public Order toEntity(OrderDTO orderDTO) {
        return new Order(orderDTO.getId(), orderDTO.getOrderNumber(), orderDTO.getUserId(), orderDTO.getStatus(), orderDTO.getCreatedAt(), orderDTO.getUpdatedAt());
    }
}
