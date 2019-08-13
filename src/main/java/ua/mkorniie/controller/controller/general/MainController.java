package ua.mkorniie.controller.controller.general;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static ua.mkorniie.service.util.directions.Pages.INDEX_PAGE;

@Slf4j
@Controller
public class MainController {

    //TODO: redirect? for anonymous - main, for admin - admin, for user - user
    @GetMapping("/")
    public String index() {
        return INDEX_PAGE.getCropPath();
    }
}
