package com.mallon.demo.User;

import com.mallon.demo.Portfolio.PortfolioMoney.Money;
import com.mallon.demo.Portfolio.PortfolioProduct.Portfolio;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private @Id Long id;
    private String fullname;
    private String password;
    private String email;

    @OneToOne(mappedBy = "user")
    private Money money;

    @OneToOne(mappedBy = "user")
    private Portfolio portfolio;



    public User(Long id, String fullname, String password, String email) {
        this.id = id;
        this.fullname = fullname;
        this.password = password;
        this.email = email;
    }


    public User(String fullname, String password, String email) {
        this.fullname = fullname;
        this.password = password;
        this.email = email;

    }


    public User() {
    }

    public User(Long id) {
        this.id = id;
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
