package com.myretail.vo;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;

public class ProductResponse {

    private int statusCode;
    private StackTraceElement[] errorMessage;

    public ProductResponse(int statusCode, StackTraceElement[] errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }


    public ProductResponse() {
        this.statusCode = 500;
        this.errorMessage = errorMessage;
    }
    public void copy(Logger log,Exception exception){
        this.statusCode = 500;
        this.errorMessage = exception.getStackTrace();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public StackTraceElement[] getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String message) {
        this.errorMessage = errorMessage;
    }
}
