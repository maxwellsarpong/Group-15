package com.mallon.demo.Portfolio.PortfolioMoney;

import com.mallon.demo.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/api")
@RestController
public class MoneyController {

    @Autowired
    private MoneyService moneyService;

    public MoneyController(MoneyService moneyService) {
        this.moneyService = moneyService;
    }


    //rest method to create portfolio money
    @PostMapping("/money")
    String createMoney(@RequestBody Money money){
        return moneyService.createMoney(money);
    }


    // method to read all portfolio products
    @GetMapping("/money")
    List<Money> getAllMoney(){
        return moneyService.getAllMoney();
    }


    // rest method to get a portfolio product by id
    @GetMapping("/money/{id}")
    Money getMoneyById(@PathVariable Long id) {
        return moneyService.getMoneyById(id);
    }


    // rest method to update a portfolio product
    @PutMapping("/money/{id}")
    String updateMoney(@RequestBody Money money, Long id){
        return moneyService.updateMoney(money, id);
    }


    // rest method to delete a portfolio product
    @DeleteMapping("/money/{id}")
    String deleteMoney(@PathVariable Long id){
       return moneyService.deleteMoney(id);
    }

}
