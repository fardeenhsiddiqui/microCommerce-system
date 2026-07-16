package com.userService.common.exception;

public class ProductServiceUnavailableException extends RuntimeException{

    public ProductServiceUnavailableException(String message) {
        super(message);
    }
}
