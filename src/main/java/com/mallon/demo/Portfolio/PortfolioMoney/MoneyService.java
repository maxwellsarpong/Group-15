package com.mallon.demo.Portfolio.PortfolioMoney;
import com.mallon.demo.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class MoneyService {

    @Autowired
    private MoneyRepository moneyRepository;

    public MoneyService(MoneyRepository moneyRepository) {
        this.moneyRepository = moneyRepository;
    }

    //method to create a portfolio money
    String createMoney(@RequestBody Money money){

        try{
            money.setUser(new User(1L));
            moneyRepository.save(money);
            return "money successfully created";
        }catch (Exception e) {
            throw e;
        }
    }


    // method to read all portfolio products
    List<Money> getAllMoney(){
        return moneyRepository.findAll();
    }


    // method to get a portfolio product by id
    Money getMoneyById(@PathVariable Long id) {
        return moneyRepository.findById(id).orElseThrow(IllegalStateException::new);
    }



    // method to update a portfolio product
    String updateMoney(@RequestBody Money money, Long id){
        return moneyRepository.findById(id).map(money1 -> {
            money.setMoney(money.getMoney());
            money.setUser(money.getUser());
            moneyRepository.save(money);
            return "portfolio product successfully updated";
        }).orElseGet(() -> {
            money.setId(id);
            moneyRepository.save(money);
            return "portfolio product successfully updated";
        });
    }


    // method to delete a portfolio product
    String deleteMoney(@PathVariable Long id){
        try{
            moneyRepository.deleteById(id);
            return "portfolio successfully deleted";
        }catch (Exception e) {
            throw e;
        }
    }
}
