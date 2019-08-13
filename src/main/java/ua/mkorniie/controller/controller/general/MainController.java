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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ua.mkorniie.controller.dao.UserRepository;
import ua.mkorniie.model.enums.Language;
import ua.mkorniie.model.enums.Role;
import ua.mkorniie.model.pojo.User;
import ua.mkorniie.model.util.directions.Pathes;

import javax.servlet.http.HttpSession;
import java.util.Locale;

import static ua.mkorniie.model.util.directions.Pages.*;

@Slf4j
@Controller
public class MainController {
    @Autowired private UserRepository userDAO;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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

    //TODO: redirect? for anonim - main, for admin - admin, for user - user
    @GetMapping("/")
    public String index() {
        return INDEX.getCropURL();
    }

//    @GetMapping("/error")
//    public String error() {
//        return GENERAL_ERROR.getCropURL();
//    }
}
