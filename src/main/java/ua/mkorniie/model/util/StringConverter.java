package ua.mkorniie.model.util;

import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import ua.mkorniie.model.enums.RoomClass;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.Optional;

@Slf4j
public class StringConverter {

    public static String decodeParameter(@NotNull String parameter) throws UnsupportedEncodingException {
        return new String(parameter.getBytes("ISO-8859-1"),"UTF8");
    }

    public static Long parseLong(String str) {
        Long id = null;
        try {
            id = Long.parseLong(str);
        } catch (Exception e) {
            log.error("Error parsing string " + str + " to Long\n"
                    + e.getMessage());
        }
        return id;
    }

    private StringConverter() {}
}
