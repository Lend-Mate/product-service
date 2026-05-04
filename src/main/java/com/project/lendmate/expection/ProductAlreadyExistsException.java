package com.project.lendmate.expection;

public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException() {}

    public ProductAlreadyExistsException(String msg) {
        super(msg);
    }
}
