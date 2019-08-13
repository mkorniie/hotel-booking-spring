package ua.mkorniie.controller.controller.general;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ua.mkorniie.model.enums.Role;
import ua.mkorniie.model.util.directions.Pathes;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class LogoutController {
    @GetMapping("/logout")
    public String logout() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.invalidate();
        return "redirect:" + Pathes.MAIN.getUrl();
    }

    @GetMapping("/success")
    public RedirectView successLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            if (auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.name()))) {
                return new RedirectView(Pathes.ADMIN_MAIN.getUrl());
            } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.USER.name()))) {
                return new RedirectView(Pathes.USER_MAIN.getUrl());
            }
        } catch (Exception e) {
            log.info("Error accessing authentication: " + e.getMessage());
        }
        return new RedirectView(Pathes.MAIN.getUrl());
    }

    //    @GetMapping("/logout")
//    protected String logout(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null){
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        logger.info("Session invalidated successfully.");
//        logger.info("User logged out successfully.");
//        return "index";
//        }
//
//    }
}
