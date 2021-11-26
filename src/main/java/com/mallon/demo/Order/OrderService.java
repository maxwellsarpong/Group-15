package com.mallon.demo.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private String exchangeName = "https://exchange.matraining.com";
    private String key = "a8e67540-d3a4-49d6-9800-76837efe24d8";

    private OrderRepository orderRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // get all orders
    List<Order> getOrders(){
        return orderRepository.findAll();
    }


    // create new orders
    String createOrder(@RequestBody Order order) {
         String exchangeName = "https://exchange.matraining.com";
         String key = "a8e67540-d3a4-49d6-9800-76837efe24d8";

        String orderToken = restTemplate.postForObject(exchangeName +"/" + key+ "/order" ,order,String.class);
        orderRepository.save(order);

        return orderToken;

        // todo (check if an order already exists)
        // todo (add tokens to the order table)
        // todo (validates order)
    }


    // localhost:8080/token/order/id
    //get an order by id
   Order getOrderById(@PathVariable String id){

        Order order = restTemplate.getForObject(exchangeName +"/" + key+ "/order/"+ id ,Order.class);
        System.out.println(order);
        return order;
        // todo (check if an order exists with the id)
    }




    // delete a single order
    void deleteOrder(@PathVariable Long id){
        orderRepository.deleteById(id);
    }


    // update an order
    Order updateOrder(@RequestBody Order order, @PathVariable Long id){
        return orderRepository.findById(id).map(order1 -> {
            order.setPrice(order.getPrice());
            order.setQuantity(order.getQuantity());

            return orderRepository.save(order);
        }).orElseGet(() -> {
            order.setOrderId(id);
            return orderRepository.save(order);
        });
    }
}

