package com.mallon.demo.Order;

import com.mallon.demo.User.User;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "orders")
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id Long OrderId;

    //@NotNull
    private String product;

    //@Min(value=1, message="quantity must be equal or greater than 1")
    private int quantity;

    //@Min(value=1, message="price must be equal or greater than 1")
    private double price;

    //@NotNull
    private String side;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;





    public Order(Long orderId, String product, int quantity, double price, String side, User user) {
        OrderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.side = side;
        this.user = user;
    }


    public Order(String product, int quantity, double price, String side, User user) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.side = side;
        this.user = user;
    }


    public Order() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getOrderId() {
        return OrderId;
    }

    public void setOrderId(Long orderId) {
        OrderId = orderId;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return quantity == order.quantity && Double.compare(order.price, price) == 0 && OrderId.equals(order.OrderId) && product.equals(order.product) && side.equals(order.side);
    }

    @Override
    public int hashCode() {
        return Objects.hash(OrderId, product, quantity, price, side);
    }

    @Override
    public String toString() {
        return "Order{" +
                "OrderId=" + OrderId +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", side='" + side + '\'' +
                '}';
    }
}
