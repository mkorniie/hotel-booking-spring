package ua.mkorniie.service.util;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class StringConverter {

    /***
     * Parses given non-null string to Long
     * @param str - string to be parsed
     * @return Optional<Long> - Long on success, empty  on failure
     */
    public static Optional<Long> parseLong(@NonNull String str) {
        log.info("Trying to convert string " + str + " to Long value");
        Optional<Long> id = Optional.empty();
        try {
            Long value = Long.parseLong(str);
            id = Optional.of(value);
        } catch (Exception e) {
            log.error("Error parsing string " + str + " to Long\n"
                    + e.getMessage());
        }
        log.info("Returning " + id);
        return id;
    }

    private StringConverter() {}
}
