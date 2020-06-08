package com.myretail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Server Error Occured")
public class MyRetailException extends Exception {

    private int errorCode;
    private String errorMessage;

    public MyRetailException() {
    }

    public MyRetailException(int errorCode,String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode= errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
