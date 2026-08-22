package com.lendmate.productservice.expection;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {}

    public ProductNotFoundException(String msg) {
        super(msg);
    }
}
