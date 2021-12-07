package com.mallon.demo.Portfolio.PortfolioProduct;

import com.mallon.demo.Portfolio.PortfolioProduct.Portfolio;
import com.mallon.demo.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Portfolio findByProduct(String product);
    List<Portfolio> findByUser(User user);
}
