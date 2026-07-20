package com.project.lendmate.expection;

public class UnknownException extends RuntimeException {

    public UnknownException() {}

    public UnknownException(String msg) {
        super(msg);
    }
}
