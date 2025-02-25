package com.example.ORDERSERVICE.controller;


import com.example.ORDERSERVICE.dto.OrderRequest;
import com.example.ORDERSERVICE.dto.OrderStatus;
import com.example.ORDERSERVICE.entity.Order;
import com.example.ORDERSERVICE.entity.OrderProduct;
import com.example.ORDERSERVICE.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {


    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/{userid}")
    public ResponseEntity<UUID> createOrder(@RequestBody OrderRequest orderRequest, @PathVariable UUID userid) throws  Exception{
        log.info("CREATING ORDER");
        return new ResponseEntity<>(orderService.createOrder(orderRequest, userid), HttpStatus.OK);
    }

    @PutMapping("/{orderid}")
    public ResponseEntity<Void> updateOrder(@RequestBody OrderStatus orderStatus,@PathVariable UUID orderid) throws  Exception{
        log.info("UPDATING ORDER");
        orderService.updateOrder(orderStatus, orderid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userid}")
    public Page<Order> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                    @PathVariable UUID userid) throws Exception{
        log.info("GETTING ALL ORDERS");
        return orderService.getAllOrders(page, size, authHeader, userid);
    }

    @GetMapping("/{userid}/{orderid}")
    public Page<OrderProduct> getAllOrderProduct(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                                 @PathVariable UUID userid, @PathVariable UUID orderid
                                                 ) throws  Exception{
        log.info("GETTING ALL PRODUCTS FROM PARTICULAR ORDER");
        return orderService.getAllOrderProduct(page, size, authHeader, userid, orderid);
    }

}
