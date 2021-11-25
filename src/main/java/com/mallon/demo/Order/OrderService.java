package com.mallon.demo.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // get all orders
    List<Order> getOrders(){
        return orderRepository.findAll();
    }

    // create new orders
    Order createOrder(@RequestBody Order order) {
        return orderRepository.save(order);
        // todo (check if an order already exists)
    }


    // localhost:8080/token/order/id
    //get an order by id
    Order getOrderById(@PathVariable Long id){
        return orderRepository.findById(id).orElse(null);
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
            order.setId(id);
            return orderRepository.save(order);
        });
    }
}

