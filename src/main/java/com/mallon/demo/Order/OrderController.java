package com.mallon.demo.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


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
    String createOrder(@RequestBody Order order)
    {
        return orderService.createOrder(order);

    }


    @GetMapping("/order/{id}")
    Order getOrder(@PathVariable String id){
        return orderService.getOrderById(id);
    }



    /*RestTemplate rest = new RestTemplate();
    String url = "https://jsonplaceholder.typicode.com/posts?userId=1";
    ResponseEntity<Post[]> entity = rest.getForEntity(url, Post[].class);
    Post[] posts = entity.getBody();

     */

    //
}
