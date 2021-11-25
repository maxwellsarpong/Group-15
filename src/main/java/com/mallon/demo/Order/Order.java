package com.mallon.demo.Order;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private @Id Long OrderId;
    private String product;
    private int quantity;
    private double price;
    private String side;


    public Order(Long id, String product, int quantity, double price, String side) {
        this.OrderId= id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.side = side;
    }


    public Order(String product, int quantity, double price, String side) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.side = side;
    }


    public Order() {
    }


    public Long getId() {
        return OrderId;
    }

    public void setId(Long id) {
        this.OrderId = id;
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
}
