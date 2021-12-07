package com.mallon.demo.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface OrderRepository extends JpaRepository<Order, Long> {

    //Optional<Order> getOrderByProduct(String product);
    //Optional<Order> getOrderByQuantity(int quantity);

}
