package ua.mkorniie.controller.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.mkorniie.service.security.HotelUserDetails;
import ua.mkorniie.service.util.directions.Pathes;

@Slf4j
@Controller
public class UserMainController {

    @GetMapping("/user/")
    public String getMain(Model model, Authentication authentication) {
        log.info("Accessing GET main controller");
        if (authentication != null) {
            HotelUserDetails userDetails = (HotelUserDetails)authentication.getDetails();
            model.addAttribute("username", userDetails.getUsername());
        }
        return Pathes.USER_MAIN.getCropPagePath();
    }
}
