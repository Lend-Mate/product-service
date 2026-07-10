package com.project.lendmate.expection;

public class ProductAvailabilityNotFound extends RuntimeException{
    public ProductAvailabilityNotFound(String message) {
        super(message);
    }
}
