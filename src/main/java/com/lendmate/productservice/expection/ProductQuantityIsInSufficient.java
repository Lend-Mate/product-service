package com.lendmate.productservice.expection;

public class ProductQuantityIsInSufficient extends RuntimeException{
    public ProductQuantityIsInSufficient(String message) {
        super(message);
    }
}