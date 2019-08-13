package ua.mkorniie.controller.controller.general;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static ua.mkorniie.service.util.directions.Pages.INDEX_PAGE;
import static ua.mkorniie.service.util.directions.Pathes.ADMIN_MAIN;
import static ua.mkorniie.service.util.directions.Pathes.USER_MAIN;

@Slf4j
@Controller
public class MainController {

    //TODO: redirect? for anonymous - main, for admin - admin, for user - user
    @GetMapping("/")
    public String index(Authentication authentication) {
        if (authentication != null) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
                return "redirect:" + USER_MAIN.getUrl();
            } else {
                return "redirect:" + ADMIN_MAIN.getUrl();
            }
        }
        return INDEX_PAGE.getCropPath();
    }
}
