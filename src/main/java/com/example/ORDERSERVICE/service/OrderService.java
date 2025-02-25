package com.example.ORDERSERVICE.service;

import com.example.ORDERSERVICE.dto.*;
import com.example.ORDERSERVICE.entity.Order;
import com.example.ORDERSERVICE.entity.OrderProduct;
import com.example.ORDERSERVICE.enums.OrderEnum;
import com.example.ORDERSERVICE.repository.OrderProductRepository;
import com.example.ORDERSERVICE.repository.OrderRepository;
import com.example.ORDERSERVICE.retrofit.APIResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
public class OrderService {

    private final APIResolver apiResolver;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    public OrderService(APIResolver apiResolver, OrderRepository orderRepository, OrderProductRepository orderProductRepository){
        this.apiResolver = apiResolver;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
    }


//    public void updateOrder(OrderStatus orderStatus, UUID id) throws Exception{
//
//
//
//        Optional<Order> existingOrder = orderRepository.findById(id);
//
//        if(existingOrder.get().getStatus().equals("CANCELLED")){
//            return;
//        }
//
//        if(existingOrder.get().getStatus().equals("DELIVERED")){
//            return;
//        }
//
//        if(!existingOrder.isPresent()){
//            throw  new Exception("ORDER DOES NOT EXIST");
//        }
//
//        existingOrder.get().setStatus(orderStatus.getStatus());
//
//        orderRepository.save(existingOrder.get());
//    }


    public void updateOrder(OrderStatus orderStatus, UUID id) throws Exception {
        Optional<Order> existingOrder = orderRepository.findById(id);

        if (!existingOrder.isPresent()) {
            throw new Exception("ORDER DOES NOT EXIST");
        }

        String currentStatus = existingOrder.get().getStatus();
        if (currentStatus.equals("CANCELLED") || currentStatus.equals("DELIVERED")) {
            throw new Exception("CANNOT UPDATE ORDER STATUS");
        }

        if (!isValidOrderStatus(orderStatus.getStatus())) {
            throw new Exception("INVALID ORDER STATUS");
        }

        existingOrder.get().setStatus(orderStatus.getStatus());
        orderRepository.save(existingOrder.get());
    }

    public boolean isValidOrderStatus(String status) {
        try {
            OrderEnum.valueOf(status);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }




    public UUID createOrder(OrderRequest orderRequest, UUID userid) throws Exception{

        log.info("CREATING ORDER");

        List<Product> availableProucts = new ArrayList<>();

        Set<UUID> addedProducts = new HashSet<>();

        for(ProductRequest productRequest : orderRequest.getProducts()){
            UUID productId = productRequest.getId();
            int quantity = productRequest.getQuantity();
            Product existingProduct = apiResolver.getProduct(productId);

            if(addedProducts.contains(existingProduct.getId())){
                throw new Exception("ADD PRODUCTS OF SAME TYPE BY INCREASING QUANTITY");
            }

            addedProducts.add(existingProduct.getId());

            existingProduct.setPrice(existingProduct.getPrice() * quantity);
            availableProucts.add(existingProduct);
        }

        Order newOrder = new Order();
        newOrder.setOrderNumber(String.valueOf(System.currentTimeMillis()));
        newOrder.setStatus("INITIATED");
        newOrder.setUserId(userid);

        Order order = orderRepository.save(newOrder);

        for(int i=0;i<availableProucts.size();i++){
            Product product = availableProucts.get(i);
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrderId(order.getId());
            orderProduct.setProductId(product.getId());
            orderProduct.setQuantity(orderRequest.getProducts().get(i).getQuantity());
            orderProduct.setAmount(product.getPrice());

            orderProductRepository.save(orderProduct);
        }

        return order.getId();
    }

    public Page<Order> getAllOrders(int page, int size, String token, UUID userid) throws Exception{

        ResponseAuth authResponse = apiResolver.verifyToken(token.substring(7));


        if(authResponse.getRole().equals("ADMIN")){
            return orderRepository.findAll(PageRequest.of(page, size));
        }

        return orderRepository.findByUserId(userid, PageRequest.of(page, size));
    }


    public Page<OrderProduct> getAllOrderProduct(int page, int size, String token, UUID userid, UUID orderid) throws  Exception{

        ResponseAuth auth = apiResolver.verifyToken(token.substring(7));

        boolean isUserExists = orderRepository.existsByUserId(userid);
        boolean isOrderExist = orderRepository.existsById(orderid);

        if(isUserExists && isOrderExist){
            return orderProductRepository.findByOrderId(orderid, PageRequest.of(page, size));
        }

        throw  new Exception("NO ORDER EXIST/INVALID USERID");

    }



}
