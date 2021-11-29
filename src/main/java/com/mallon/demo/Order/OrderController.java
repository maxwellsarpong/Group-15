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


    // rest controller to get all  orders
    @GetMapping("/order")
    List<Order> getOrderList() {
        return orderService.getOrders();
    }



    // rest controller to create an order
    @PostMapping("/order")
    String createOrder(@RequestBody Order order)
    {
        return orderService.createOrder(order);
    }


    // rest controller to getting an order by the id
    @GetMapping("/order/{id}")
    Order getOrder(@PathVariable String id)
    {
        return orderService.getOrderById(id);
    }

    // rest controller to deleting an order by the id
    @DeleteMapping("/order/{id}")
    boolean deleteOrder(@PathVariable Long id){
         return orderService.deleteOrder(id);
    }

    //rest controller to update an order
    @PutMapping("/order/{id}")
    boolean updateOrder(@RequestBody Order order,@PathVariable Long id){
        return orderService.updateOrder(order, id);
    }



    /*RestTemplate rest = new RestTemplate();
    String url = "https://jsonplaceholder.typicode.com/posts?userId=1";
    ResponseEntity<Post[]> entity = rest.getForEntity(url, Post[].class);
    Post[] posts = entity.getBody();

     */

    //
}
