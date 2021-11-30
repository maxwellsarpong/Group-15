package com.mallon.demo.Portfolio.PortfolioProduct;

import com.mallon.demo.Portfolio.PortfolioProduct.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
