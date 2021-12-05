package com.mallon.demo.Portfolio.PortfolioProduct;

import com.mallon.demo.User.User;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "portfolio_products")
public class Portfolio {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id Long id;

    @NotNull
    private String product;


   @Min(value=0, message="price must be equal or greater than 0")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Portfolio(Long id, String product, int quantity, User user) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.user = user;
    }


    public Portfolio(String product, int quantity, User user) {
        this.product = product;
        this.quantity = quantity;
        this.user = user;
    }

    public Portfolio() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
        Portfolio portfolio = (Portfolio) o;
        return quantity == portfolio.quantity && Objects.equals(id, portfolio.id) && product.equals(portfolio.product) && Objects.equals(user, portfolio.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity, user);
    }
}
