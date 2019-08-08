package ua.mkorniie;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.mkorniie.model.enums.Language;
import ua.mkorniie.model.util.directions.Pages;

public class Test {
//    public static void main(String[] args) {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//        System.out.println(encoder.encode("123"));
//    }

    public static void main(String[] args) {
        assert(Pages.ACCESS_ERROR_PAGE.getCropURL().equals("no-rights"));
        System.out.println("Works!");
    }
}
