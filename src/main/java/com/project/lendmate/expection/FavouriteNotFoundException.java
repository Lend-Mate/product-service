package com.project.lendmate.expection;

public class FavouriteNotFoundException extends RuntimeException {
    public FavouriteNotFoundException() {}
    public FavouriteNotFoundException(String msg) {
        super(msg);
    }
}
