package ua.mkorniie.service.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class Rounder {
    @Getter private static final int			digitsAfterPoint = 2;

    /**
     * Rounds (mathimatically, e.g. 0.055 will be rounded to 0.06) the given double price,
     * with giver precision (digitsAfterPoint variable).
     * @param price - price to be rounded
     * @return double with digitsAfterPoint precision (set to 2 - Database price precision).
     */
    public static double round(double price) {
        log.info("Rounding double " + price + " to " + digitsAfterPoint + " numbers after point");
        BigDecimal bd = new BigDecimal(Double.toString(price));
        bd = bd.setScale(digitsAfterPoint, RoundingMode.HALF_UP);
        log.info("Rounded double: " + bd.doubleValue());
        return bd.doubleValue();
    }

    private Rounder(){}
}
