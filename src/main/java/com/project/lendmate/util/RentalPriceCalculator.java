package com.project.lendmate.util;

import com.project.lendmate.model.Enum.RentalPeriod;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class RentalPriceCalculator {

    private RentalPriceCalculator() {}

    public static BigDecimal calculateTotalPrice(BigDecimal monthlyPrice, RentalPeriod period) {
        BigDecimal discount = monthlyPrice.multiply(period.getDiscountRate());
        return monthlyPrice.subtract(discount).setScale(2, RoundingMode.HALF_UP);
    }
}
