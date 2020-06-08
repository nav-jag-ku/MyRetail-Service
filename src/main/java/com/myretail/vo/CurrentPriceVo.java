package com.myretail.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CurrentPriceVo {
    @JsonProperty(value="value")
    private String value;
    @JsonProperty(value="currency_code")
    private String currencyCode;

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

    @Override
    public String toString() {
        return "CurrentPriceVO [value=" + value + ", currencyCode=" + currencyCode + "]";
    }

}
