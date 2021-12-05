package com.mallon.demo.Portfolio.PortfolioProduct;

import com.mallon.demo.Portfolio.PortfolioProduct.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Optional<Portfolio> findByProduct(String product);
    Optional<Portfolio> findByQuantity(int quantity);
}
