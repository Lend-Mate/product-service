package com.lendmate.productservice.expection;

public class FavouriteNotFoundException extends RuntimeException {
    public FavouriteNotFoundException() {}
    public FavouriteNotFoundException(String msg) {
        super(msg);
    }
}
