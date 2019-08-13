package ua.mkorniie.model.util;

import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringConverter {

    public static Long parseLong(@NotNull String str) {
        log.info("Trying to convert string " + str + " to Long value");
        Long id = null;
        try {
            id = Long.parseLong(str);
        } catch (Exception e) {
            log.error("Error parsing string " + str + " to Long\n"
                    + e.getMessage());
        }
        log.info("Returning " + id);
        return id;
    }

    private StringConverter() {}
}
