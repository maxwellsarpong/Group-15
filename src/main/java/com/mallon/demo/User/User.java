package com.mallon.demo.User;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mallon.demo.Order.Order;
import com.mallon.demo.Portfolio.PortfolioMoney.Money;
import com.mallon.demo.Portfolio.PortfolioProduct.Portfolio;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id Long id;
    private String fullname;
    private String username;
    private String password;

    @Email
    private String email;

    @OneToOne(mappedBy = "user")
    private Money money;

    @OneToMany(mappedBy = "user", fetch=FetchType.EAGER)
    private List<Portfolio> portfolio = new ArrayList<Portfolio>();


    @OneToMany(mappedBy = "user")
    private List<Order> order = new ArrayList<Order>();


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long id, String fullname, String username, String password, String email) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.email = email;
    }


    public User(String fullname, String password, String username,String email) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.email = email;

    }


    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public <T> User(String username, String password, List<T> emptyList) {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && fullname.equals(user.fullname) && password.equals(user.password) && email.equals(user.email);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, fullname, password, email);
    }
}
