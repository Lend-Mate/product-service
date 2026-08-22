package com.lendmate.productservice.expection;

public class UnknownException extends RuntimeException {

    public UnknownException() {}

    public UnknownException(String msg) {
        super(msg);
    }
}
