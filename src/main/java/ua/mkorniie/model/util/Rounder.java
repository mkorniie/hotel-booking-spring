package ua.mkorniie.model.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Rounder {
    private static final int			digitsAfterPoint = 2;

    public static double round(double price) {
        BigDecimal bd = new BigDecimal(Double.toString(price));
        bd = bd.setScale(digitsAfterPoint, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private Rounder(){}
}
