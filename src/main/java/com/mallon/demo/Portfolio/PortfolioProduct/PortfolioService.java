package com.mallon.demo.Portfolio.PortfolioProduct;

import com.mallon.demo.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class PortfolioService {

    private PortfolioRepository portfolioRepository;

    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    //method to create a portfolio product
    String createPortfolio(@RequestBody Portfolio portfolio){
        try{
            portfolio.setUser(new User(2L));
            portfolioRepository.save(portfolio);
            return "portfolio successfully created";
        }catch (Exception e) {
            throw e;
        }
    }


    // method to read all portfolio products
    List<Portfolio> getAllPortfolio(){
        return portfolioRepository.findAll();
    }



    // method to get a portfolio product by id
    Portfolio getPortfolioById(@PathVariable Long id) {
        return portfolioRepository.findById(id).orElseThrow(IllegalStateException::new);
    }



    // method to update a portfolio product
    String updatePortfolio(@RequestBody Portfolio portfolio, Long id){
        return portfolioRepository.findById(id).map(port1 -> {
            portfolio.setProduct(portfolio.getProduct());
            portfolio.setQuantity(portfolio.getQuantity());
            portfolio.setUser(portfolio.getUser());
            portfolioRepository.save(portfolio);
            return "portfolio product successfully updated";
        }).orElseGet(() -> {
            portfolio.setId(id);
            portfolioRepository.save(portfolio);
            return "portfolio product successfully updated";
        });
    }


    // method to delete a portfolio product
    String deletePortfolio(@PathVariable Long id){
        try{
            portfolioRepository.deleteById(id);
            return "portfolio successfully deleted";
        }catch (Exception e) {
            throw e;
        }
    }
}
