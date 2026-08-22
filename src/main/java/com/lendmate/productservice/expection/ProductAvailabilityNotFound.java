package com.lendmate.productservice.expection;

public class ProductAvailabilityNotFound extends RuntimeException{
    public ProductAvailabilityNotFound(String message) {
        super(message);
    }
}
