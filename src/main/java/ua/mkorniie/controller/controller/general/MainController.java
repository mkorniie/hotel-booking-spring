package ua.mkorniie.controller.controller.general;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;
import ua.mkorniie.controller.dao.UserRepository;
import ua.mkorniie.model.enums.Role;
import ua.mkorniie.model.util.directions.Pathes;

import static ua.mkorniie.model.util.directions.Pages.*;
import static org.slf4j.LoggerFactory.getLogger;

@Slf4j
@Controller
public class MainController {
    private static final Logger logger = getLogger(MainController.class);
    @Autowired private UserRepository userRepository;

//    @GetMapping("/main")
//    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
//        model.addAttribute("name", name);
//
//        List<User> users = userRepository.findByName(name);
//        Long id = -1L;
//        for (User u : users) {
//            id = u.getId();
//        }
//        model.addAttribute("id", id);
//        return "greeting";
//    }

    @GetMapping("/")
    public String index() {
        return INDEX.getCropURL();
    }

    @GetMapping("/error")
    public String error() {
        return GENERAL_ERROR.getCropURL();
    }

    @GetMapping("/register")
    public String register() {
        return REGISTER.getCropURL();
    }

    @GetMapping("/login")
    public String login() {
        return LOGIN.getCropURL();
    }

    @GetMapping("/logout")
    public String logout() {
        return index();
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
//        logger.info("Session inalidated successfully.");
//        logger.info("User logged out successfully.");
//        return "index";
//        }
//
//    }

}
