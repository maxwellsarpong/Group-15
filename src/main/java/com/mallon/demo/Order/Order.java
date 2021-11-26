package com.mallon.demo.Order;

import javax.persistence.*;
import java.util.List;
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
    private int cumulatitiveQuantity;


    public Order(Long orderId, String product, int quantity, double price, String side, int cumulatitiveQuantity) {
        OrderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.side = side;
        this.cumulatitiveQuantity = cumulatitiveQuantity;
    }


    public Order(String product, int quantity, double price, String side, int cumulatitiveQuantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.side = side;
        this.cumulatitiveQuantity = cumulatitiveQuantity;
    }


    public Order() {
    }


    public int getCumulatitiveQuantity() {
        return cumulatitiveQuantity;
    }

    public void setCumulatitiveQuantity(int cumulatitiveQuantity) {
        this.cumulatitiveQuantity = cumulatitiveQuantity;
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
                ", cumulatitiveQuantity=" + cumulatitiveQuantity +
                '}';
    }
}
