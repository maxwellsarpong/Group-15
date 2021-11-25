package com.mallon.demo.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    // get all  orders
    @GetMapping("/order")
    List<Order> getOrderList() {
        return orderService.getOrders();
    }

    // create an order
    @PostMapping("/order")
    Order createOrder(@RequestBody Order order){
        return orderService.createOrder(order);
    }


    //
}
