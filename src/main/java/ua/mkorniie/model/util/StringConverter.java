package ua.mkorniie.model.util;

import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;
import ua.mkorniie.model.enums.RoomClass;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.Optional;

public class StringConverter {
    private static final Logger logger = Logger.getLogger(StringConverter.class);

    public static String decodeParameter(@NotNull String parameter) throws UnsupportedEncodingException {
        return new String(parameter.getBytes("ISO-8859-1"),"UTF8");
    }

    private StringConverter() {}
}
