package ua.mkorniie.controller.controller.general;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.mkorniie.controller.dao.UserRepository;

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
        return INDEX_PAGE.getCropURL();
    }

//    @GetMapping("/error")
//    public String error() {
//        return GENERAL_ERROR.getCropURL();
//    }
}
