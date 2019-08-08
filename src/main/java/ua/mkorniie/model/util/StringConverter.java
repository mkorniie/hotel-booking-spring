package ua.mkorniie.model.util;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import ua.mkorniie.model.enums.RoomClass;

import java.sql.Date;
import java.util.Optional;

public class StringConverter {
    private static final Logger logger = Logger.getLogger(StringConverter.class);

    public static Optional<Integer> strToInt(@NotNull String str) {
        Optional<Integer> result = Optional.empty();
        try {
            result = Optional.of(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            logger.error("Illegal argument: trying to convert " + str + " to " + Integer.class + ". Fail");
        }
        return result;
    }

    public static Optional<Date> strToDate(@NotNull String str) {
        Optional<Date> result = Optional.empty();
        try {
            result = Optional.of(Date.valueOf(str));
        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument: trying to convert " + str + " to sql.Date. Fail");
        }
        return result;
    }

    public static Optional<RoomClass> strToRoomSlass(@NotNull String str) {
        Optional<RoomClass> result = Optional.empty();
        try {
            result = Optional.of(RoomClass.valueOf(str));
        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument: trying to convert " + str + " to " + RoomClass.class + ". Fail");
        }
        return result;
    }

    private StringConverter() {}
}
