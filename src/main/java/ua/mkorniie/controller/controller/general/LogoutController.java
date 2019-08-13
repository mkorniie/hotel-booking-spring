package ua.mkorniie.controller.controller.general;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ua.mkorniie.service.util.directions.Pathes;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class LogoutController {
    @GetMapping("/logout")
    public String logout(Authentication authentication) {
        if (authentication == null) {
            return "redirect:" + Pathes.MAIN.getUrl();
        }

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.invalidate();
        return "redirect:" + Pathes.MAIN.getUrl();
    }
}
