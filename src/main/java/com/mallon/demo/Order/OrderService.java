package com.mallon.demo.Order;

import com.mallon.demo.User.User;
import com.mallon.demo.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


@Service
public class OrderService {

    private String exchangeName = "https://exchange.matraining.com";
    private String key = "a8e67540-d3a4-49d6-9800-76837efe24d8";
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RestTemplate restTemplate;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // get all orders
    List<Order> getOrders(){
        return orderRepository.findAll();
    }


    // create new orders
    @Transactional
    String createOrder(@RequestBody Order order) {
            //String orderToken = restTemplate.postForObject(exchangeName +"/" + key+ "/order" ,order,String.class);

        try{
            String orderToken = restTemplate.postForObject(exchangeName +"/" + key+ "/order" ,order,String.class);
            order.setUser(new User(1L));
            orderRepository.save(order);
            return "order successfully created. token: " + orderToken;
        }catch (Exception e) {
            throw e;
        }

           /* order.setUser(new User(1l));
            orderRepository.save(order);
            return orderToken;

            */

    }


    //get an order by id
   Order getOrderById(@PathVariable String id){
        try {
            Order order = restTemplate.getForObject(exchangeName +"/" + key+ "/order/"+ id ,Order.class);
            return order;
        } catch (Exception e) {
            throw e;
        }

    }


    // delete a single order
    boolean deleteOrder(@PathVariable Long id){

        try{
            orderRepository.deleteById(id);
            return true;
        }catch (Exception e) {
            throw e;
        }

    }


    // update an order
    boolean updateOrder(@RequestBody Order order, @PathVariable Long id){
        orderRepository.findById(id).map(order1 -> {
            order.setPrice(order.getPrice());
            order.setQuantity(order.getQuantity());

            orderRepository.save(order);
            return true;
        }).orElseGet(() -> {
            order.setOrderId(id);
            orderRepository.save(order);
            return true;
        });
        return true;
    }
}

