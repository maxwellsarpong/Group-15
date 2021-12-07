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
        List<Portfolio> portfolio = portfolioRepository.findByUser(new User(1L));
        //Optional<Portfolio> item = portfolio.stream().filter(v -> v.getProduct() == "AAPL").findFirst();
        Optional <Portfolio> item = portfolio.stream().filter(v->v.getProduct()== order.getProduct()).findFirst();
        System.out.println(portfolio);

        //String orderToken = restTemplate.postForObject(exchangeName +"/" + key+ "/order" ,order,String.class);

        /*
         allow transactions (buy or sell) to go through the exchange
         only when the user has sufficient money

        */
            if((money.getMoney() > order.getPrice()) || order.getSide() == "BUY"){
                double totalPurchase = order.getPrice() * order.getQuantity();
                // allow transactions to go through
                    switch (order.getSide()){
                        case "BUY":
                            if((order.getPrice() >= 1.0)){
                                try{
                                    String orderToken = restTemplate.postForObject(exchangeName2 +"/" + key+ "/order" ,order,String.class);

                                    double m = money.getMoney() - totalPurchase;
                                    money.setMoney(m);
                                    moneyRepository.save(money);

                                    int userPortfolioQty = portfolio.stream().filter(v -> v.getProduct().startsWith("AAPL")).mapToInt(Portfolio::getQuantity).sum();
                                    int currentQuantity =  userPortfolioQty+ order.getQuantity();


                                    System.out.println(currentQuantity);
                                    //TODO
                                    //update old quantity
                                    //check stream to enable access to current quantity

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

                                    double m = money.getMoney() - totalPurchase;
                                    money.setMoney(m);
                                    moneyRepository.save(money);
                                    order.setUser(new User(1L));
                                    orderRepository.save(order);
                                    return "order successfully created exchange 1. token: " + orderToken;
                                }catch (Exception e) {
                                    throw e;
                                }
                            }

                        /*

                         */

                        case "SELL":
                            System.out.println("hello james");
                            return "hello sell";

                        default:
                            System.out.println("hello");
                            return "hello default";
                    }

            }


            else if(order.getSide() == "SELL")
            {
                switch(order.getProduct()){
                    case "AAPL":
                        return "hello";
                    default:
                        return "hello from default";
                }
            }


            else {
                /*
                disallow transactions (buying of a stock) to go through the exchange when
                a user has insufficient funds. alert him/her by a message
                */
                return "Insufficient funds to buy this stock. Please try later";
            }

           /* if((order.getPrice() > 1.0)){
                try{
                    String orderToken = restTemplate.postForObject(exchangeName2 +"/" + key+ "/order" ,order,String.class);
                    double m = money.getMoney() - order.getPrice();
                    money.setMoney(m);
                    moneyRepository.save(money);
                    order.setUser(new User(1L));
                    orderRepository.save(order);
                    return "order successfully created on exchange 2. token: " + orderToken;
                }catch (Exception e) {
                    throw e;
                }
            }else{
                try{
                    String orderToken = restTemplate.postForObject(exchangeName +"/" + key+ "/order" ,order,String.class);
                    double m = money.getMoney() - order.getPrice();
                    money.setMoney(m);
                    moneyRepository.save(money);
                    order.setUser(new User(1L));
                    orderRepository.save(order);
                    return "order successfully created exchange 1. token: " + orderToken;
                }catch (Exception e) {
                    throw e;
                }
            }
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

