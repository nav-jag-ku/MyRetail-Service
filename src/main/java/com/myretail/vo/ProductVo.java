package com.myretail.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@JsonIgnoreProperties(ignoreUnknown=true)
public class ProductVo {

    @JsonProperty(value="id")
    int productId;

    @JsonProperty(value="name")
    String name;

    @JsonProperty(value="current_price")
    CurrentPriceVo currentPrice;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrentPriceVo getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(CurrentPriceVo currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        return "ProductResponse [productId=" + productId + ", name=" + name + ", currentPrice=" + currentPrice + "]";
    }
}
