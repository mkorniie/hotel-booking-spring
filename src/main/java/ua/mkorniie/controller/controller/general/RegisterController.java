package ua.mkorniie.controller.controller.general;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mkorniie.controller.service.view.anonymous.RegisterService;
import ua.mkorniie.model.util.directions.Pathes;

@Slf4j
@Controller
public class RegisterController {

    private RegisterService service;

    @Autowired
    public RegisterController(RegisterService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public String registerGet() {
        return Pathes.REGISTER.getCropPagePath();
    }

    @PostMapping("/register")
    public String registerPost(@RequestParam(name = "name") String name,
                               @RequestParam(name = "email") String email,
                               @RequestParam(name="password") String pass) {
        service.register(name, email, pass);
        return "redirect:" + Pathes.LOGIN.getUrl();
    }
}
