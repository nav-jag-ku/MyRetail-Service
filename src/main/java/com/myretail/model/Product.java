package com.myretail.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "products")
public class Product {

    @Id
    private int productId;
    private String  name;
    private CurrentPrice currentPrice;

    public Product(int productId, CurrentPrice currentPrice,String name) {
        this.productId = productId;
        this.currentPrice = currentPrice;
        this.name = name;
    }

    public Product() {

    }

    public int getProductId() {
        return productId;
    }


    public void setProductId(int productId) {
        this.productId = productId;
    }

    public CurrentPrice getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(CurrentPrice currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
