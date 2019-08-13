package ua.mkorniie.model.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class Rounder {
    private static final int			digitsAfterPoint = 2;

    public static double round(double price) {
        log.info("Rounding double " + price + " to " + digitsAfterPoint + " numbers after point");
        BigDecimal bd = new BigDecimal(Double.toString(price));
        bd = bd.setScale(digitsAfterPoint, RoundingMode.HALF_UP);
        log.info("Rounded double: " + bd.doubleValue());
        return bd.doubleValue();
    }

    private Rounder(){}
}
