package ua.mkorniie.controller.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ua.mkorniie.service.util.directions.Pathes;

@Slf4j
@Controller
public class UserMainController {

    @GetMapping("/user/")
    public String getMain() {
        log.info("Accessing GET main controller");
        return Pathes.USER_MAIN.getCropPagePath();
    }
}
