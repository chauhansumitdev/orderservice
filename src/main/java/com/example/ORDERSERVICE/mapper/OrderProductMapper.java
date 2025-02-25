package com.example.ORDERSERVICE.mapper;

import com.example.ORDERSERVICE.dto.OrderProductDTO;
import com.example.ORDERSERVICE.entity.OrderProduct;
import org.springframework.stereotype.Component;

@Component
public class OrderProductMapper {

    public OrderProductDTO toDTO(OrderProduct orderProduct) {
        return new OrderProductDTO(
                orderProduct.getId(),
                orderProduct.getOrderId(),
                orderProduct.getProductId(),
                orderProduct.getAmount(),
                orderProduct.getQuantity(),
                orderProduct.getCreatedAt(),
                orderProduct.getUpdatedAt()
        );
    }

    public OrderProduct toEntity(OrderProductDTO orderProductDTO) {
        return new OrderProduct(
                orderProductDTO.getId(),
                orderProductDTO.getOrderId(),
                orderProductDTO.getProductId(),
                orderProductDTO.getAmount(),
                orderProductDTO.getQuantity(),
                orderProductDTO.getCreatedAt(),
                orderProductDTO.getUpdatedAt()
        );
    }
}
