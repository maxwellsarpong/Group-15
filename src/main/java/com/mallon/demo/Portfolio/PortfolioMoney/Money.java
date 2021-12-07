package com.mallon.demo.Portfolio.PortfolioMoney;

import com.mallon.demo.User.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "portfolio_money")
public class Money {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  @Id Long id;
    private double money;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Money(Long id, double money, User user) {
        this.id = id;
        this.money = money;
        this.user = user;
    }


    public Money(double money, User user) {
        this.money = money;
        this.user = user;
    }


    public Money() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money1 = (Money) o;
        return Double.compare(money1.money, money) == 0 && id.equals(money1.id) && Objects.equals(user, money1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, money, user);
    }
}
