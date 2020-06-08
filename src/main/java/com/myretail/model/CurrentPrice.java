package com.myretail.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class CurrentPrice {

    private String value;
    private String currencyCode;

    public CurrentPrice(String value, String currencyCode) {
        this.value = value;
        this.currencyCode = currencyCode;
    }

    public CurrentPrice() {

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
