package ua.mkorniie.controller.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user/")
public class UserMainController {

    @GetMapping("/user/")
    public String getMain() {
        log.info("Accessing GET main controller");
        return "user/user-main";
    }
}
