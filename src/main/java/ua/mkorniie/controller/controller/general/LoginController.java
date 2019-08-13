package ua.mkorniie.controller.controller.general;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mkorniie.controller.service.view.anonymous.LoginService;

import static ua.mkorniie.model.util.directions.Pages.*;


@Slf4j
@Controller
public class LoginController {
    private final LoginService service;

    public LoginController(LoginService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String loginGet() {
        return LOGIN_PAGE.getCropURL();
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam(name = "username") String username,
                            @RequestParam(name = "password") String password) {
        return service.login(username, password);
    }
}
