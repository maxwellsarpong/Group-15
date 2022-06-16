package com.mallon.demo.OrderBook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;

@RequestMapping(path = "/api")
@RestController
public class OrderBookController {
    private String url1 = "https://exchange.matraining.com/orderbook";
    private String url2 = "https://exchange2.matraining.com/orderbook";

    @Autowired
    RestTemplate restTemplate;

    //method to get all the order book
    @GetMapping("/orderbook")
    List<OrderBook> getAllOrderBook(){
        List<OrderBook> orderBook1 = restTemplate.getForObject(url1, List.class);
        return orderBook1;
    }

    //method to get an order book by a product
    @GetMapping("/orderbook/{ticker}")
    OrderBook getOrderBookByTicker(@PathVariable String ticker) throws JsonProcessingException {
        String order = restTemplate.getForObject(
                "https://exchange.matraining.com/orderbook/" +ticker, String.class);
        ObjectMapper obj = new ObjectMapper();
        return obj.readValue(order,  OrderBook.class);
    }


    //method to get a product by a status
    @GetMapping("/orderbook/{ticker}/{status}")
    OrderBook getOrderBookByTicker(@PathVariable String ticker, String status) throws JsonProcessingException {
        String order = restTemplate.getForObject(
                "https://exchange.matraining.com/orderbook/" + ticker + "/" + status, String.class);
        ObjectMapper obj = new ObjectMapper();
        return obj.readValue(order,  OrderBook.class);
    }

}
