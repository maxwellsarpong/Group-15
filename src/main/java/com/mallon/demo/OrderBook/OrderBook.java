package com.mallon.demo.OrderBook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderBook {

   @JsonProperty("product")
     private String product;

    @JsonProperty("quantity")
     private  int quantity;

    @JsonProperty("price")
     private double price;

    @JsonProperty("side")
     private String side;

    @JsonProperty("executions")
     private List executions;

    @JsonProperty("cumulatitiveQuantity")
     private int cumulatitiveQuantity;


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

 public List getExecutions() {
  return executions;
 }

 public void setExecutions(List executions) {
  this.executions = executions;
 }

 public int getCumulatitiveQuantity() {
  return cumulatitiveQuantity;
 }

 public void setCumulatitiveQuantity(int cumulatitiveQuantity) {
  this.cumulatitiveQuantity = cumulatitiveQuantity;
 }
}
