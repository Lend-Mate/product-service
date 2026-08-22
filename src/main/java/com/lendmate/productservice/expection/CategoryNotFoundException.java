package com.lendmate.productservice.expection;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException() {}

    public CategoryNotFoundException(String msg) {
        super(msg);
    }
}
