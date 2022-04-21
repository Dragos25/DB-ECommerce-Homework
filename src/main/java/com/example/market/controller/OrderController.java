package com.example.market.controller;

import com.example.market.model.Order;
import com.example.market.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    //get an user's order history
    @GetMapping("/{userId}")
    public List<Order> findAllByUser(@PathVariable("userId") Integer userId){
        return orderService.findAllByUser(userId);
    }

    //place an order
    //move all products currenly in user's cart to an order and add it to it's order history
    @PostMapping("/placeOrder/{userId}")
    public Order placeOrder(@PathVariable("userId") Integer userId){
        return orderService.placeOrder(userId);
    }

}
