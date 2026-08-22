package com.lendmate.productservice.expection;

public class CategoryAlreadyExistsException extends RuntimeException {

    public CategoryAlreadyExistsException() {}

    public CategoryAlreadyExistsException(String msg) {
        super(msg);
    }
}
