package ua.mkorniie.controller.controller.general;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ua.mkorniie.controller.dao.UserRepository;
import ua.mkorniie.model.enums.Language;
import ua.mkorniie.model.enums.Role;
import ua.mkorniie.model.exceptions.LanguageNotFoundException;
import ua.mkorniie.model.pojo.User;
import ua.mkorniie.model.util.directions.Pathes;

import java.util.Locale;

import static ua.mkorniie.model.util.directions.Pages.*;
import static org.slf4j.LoggerFactory.getLogger;

@Slf4j
@Controller
public class MainController {
    private static final Logger logger = getLogger(MainController.class);
    @Autowired private UserRepository userDAO;

//    @GetMapping("/main")
//    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
//        model.addAttribute("name", name);
//
//        List<User> users = userDAO.findByName(name);
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
    public String registerGet() {
        return REGISTER.getCropURL();
    }

    //TODO: don't forget to add full commit info
    @PostMapping("/register")
    public String registerPost(@RequestParam(name = "name", required=true) String name,
                               @RequestParam(name = "email", required=true) String email,
                               @RequestParam(name="password", required=true) String pass) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Locale locale = LocaleContextHolder.getLocale();

        User newUser = null;
        try {
            newUser = new User(name, Role.USER, encoder.encode(pass), email, Language.of(locale));
            userDAO.save(newUser);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return newUser == null ? registerGet() : LOGIN.getCropURL();
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
