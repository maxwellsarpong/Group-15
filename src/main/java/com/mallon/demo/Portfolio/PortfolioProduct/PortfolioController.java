package com.mallon.demo.Portfolio.PortfolioProduct;

import com.mallon.demo.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class PortfolioController {

    private PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    // rest method to create a portfolio
    @PostMapping("/portfolio")
    String createPortfolio(@RequestBody Portfolio portfolio){
        return portfolioService.createPortfolio(portfolio);
    }

    // rest method to get all the portfolio
    @GetMapping("/portfolio")
    List<Portfolio> getAllPortfolio(){
        return portfolioService.getAllPortfolio();
    }

    // rest method to get a portfolio by ID
    @GetMapping("/portfolio/{id}")
    Portfolio getPortfolioById(@PathVariable Long id){
        return portfolioService.getPortfolioById(id);
    }


    // rest method to update a portfolio
    @PutMapping("/portfolio")
    String UpdatePortfolio(@RequestBody Portfolio portfolio, Long id) {
        return portfolioService.updatePortfolio(portfolio, id);
    }

    // rest method to delete a portfolio
    @DeleteMapping("/portfolio/{id}")
    String deletePortfolio(@PathVariable Long id){
        return portfolioService.deletePortfolio(id);
    }
}
