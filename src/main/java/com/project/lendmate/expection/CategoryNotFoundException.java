package com.project.lendmate.expection;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException() {}

    public CategoryNotFoundException(String msg) {
        super(msg);
    }
}
