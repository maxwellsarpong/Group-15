package com.mallon.demo.Portfolio.PortfolioMoney;

import com.mallon.demo.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface MoneyRepository extends JpaRepository<Money, Long> {

    Money findByUser(User user);
}
