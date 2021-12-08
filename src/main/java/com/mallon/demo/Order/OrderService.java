package com.mallon.demo.Order;

import com.mallon.demo.Portfolio.PortfolioMoney.Money;
import com.mallon.demo.Portfolio.PortfolioMoney.MoneyRepository;
import com.mallon.demo.Portfolio.PortfolioProduct.Portfolio;
import com.mallon.demo.Portfolio.PortfolioProduct.PortfolioRepository;
import com.mallon.demo.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
public class OrderService {

    private String exchangeName = "https://exchange.matraining.com";
    private String exchangeName2 = "https://exchange2.matraining.com";
    private String key = "a8e67540-d3a4-49d6-9800-76837efe24d8";
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    MoneyRepository moneyRepository;

    @Autowired
    PortfolioRepository portfolioRepository;

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
    @Transactional
    String createOrder(@RequestBody Order order) {
        Money money = moneyRepository.findByUser(new User(1L));
        List<Portfolio> portfolioList = portfolioRepository.findByUser(new User(1L));
        Optional<Portfolio> product = portfolioList.stream().filter(v -> v.getProduct().equals(order.getProduct())).findFirst();
        int userPortfolioQty = portfolioList.stream().filter(v -> v.getProduct().equals(order.getProduct())).mapToInt(Portfolio::getQuantity).sum();
        Long itemId = portfolioList.stream().filter(obj->obj.getProduct().equals(order.getProduct())).mapToLong(Portfolio::getId).sum();

        double costPrice = order.getPrice() * order.getQuantity();
        double sellingPrice = order.getPrice() * order.getQuantity();

        /*
         allow transactions (buy or sell) to go through the exchange
         only when the user has sufficient money

        */

                //double totalPurchase = order.getPrice() * order.getQuantity();
                // allow transactions to go through
                    switch (order.getSide()){
                        case "BUY":
                            if(money.getMoney() > costPrice){
                                if((order.getPrice() >= 1.0)){
                                    try{
                                        String orderToken = restTemplate.postForObject(exchangeName2 +"/" + key+ "/order" ,order,String.class);

                                        double m = money.getMoney() - costPrice;
                                        money.setMoney(m);
                                        moneyRepository.save(money);

                                    /*
                                        checks to see if the client
                                        already has the stock in his portfolio
                                        if he owns it, then increase the quantity
                                        if not create the stock
                                    */

                                        if(product.isPresent()){

                                            int currentQuantity =  userPortfolioQty+ order.getQuantity();
                                            portfolioList.stream()
                                                    .filter(obj->obj.getId()== itemId)
                                                    .findFirst()
                                                    .ifPresent(o->o.setQuantity(currentQuantity));
                                        }

                                        else if(!product.isPresent()){
                                            Portfolio p = new Portfolio(order.getProduct(),order.getQuantity(),new User(1l));
                                            portfolioRepository.save(p);

                                        }

                                        //save the order which has been placed into the database
                                        order.setUser(new User(1L));
                                        orderRepository.save(order);
                                        return "order successfully created on exchange 2. token: " + orderToken;

                                    }catch (Exception e) {
                                        throw e;
                                    }
                                }

                                else{
                                    try{
                                        String orderToken = restTemplate.postForObject(exchangeName +"/" + key+ "/order" ,order,String.class);

                                        double m = money.getMoney() - costPrice;
                                        money.setMoney(m);
                                        moneyRepository.save(money);

                                     /*
                                        checks to see if the client
                                        already has the stock in his portfolio
                                        if he owns it, then increase the quantity
                                        if not create the stock
                                    */

                                        if(product.isPresent()){

                                            int currentQuantity =  userPortfolioQty+ order.getQuantity();
                                            portfolioList.stream()
                                                    .filter(obj->obj.getId()== itemId)
                                                    .findFirst()
                                                    .ifPresent(o->o.setQuantity(currentQuantity));
                                        }

                                        else if(!product.isPresent()){
                                            Portfolio p = new Portfolio(order.getProduct(),order.getQuantity(),new User(1l));
                                            portfolioRepository.save(p);

                                        }

                                        //save the order which has been placed into the database
                                        order.setUser(new User(1L));
                                        orderRepository.save(order);
                                        return "order successfully created on exchange 1. token: " + orderToken;
                                    }catch (Exception e) {
                                        throw e;
                                    }
                                }
                            }else{
                                return "Insufficient funds to buy this stock. Please try later";
                            }

                        case "SELL":
                            if((product.isPresent()) && (userPortfolioQty >= order.getQuantity())){
                                String orderToken = restTemplate.postForObject(exchangeName2 +"/" + key+ "/order" ,order,String.class);
                                int currentQty = userPortfolioQty - order.getQuantity();
                                portfolioList.stream()
                                        .filter(obj->obj.getId()== itemId)
                                        .findFirst()
                                        .ifPresent(o->o.setQuantity(currentQty));

                                double m = money.getMoney() + sellingPrice;
                                money.setMoney(m);
                                moneyRepository.save(money);

                                return "item successfully sold. item token: " + orderToken;
                            }else{
                                return "Sorry, transaction failed. Insufficient quantity or unavailable product";
                            }

                        default:
                            return "please specify your side";
                    }

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

