package ua.mkorniie.controller.controller.general;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mkorniie.controller.service.view.anonymous.RegisterService;

import static ua.mkorniie.model.util.directions.Pages.REGISTER;

@Slf4j
@Controller
public class RegisterController {

    private final RegisterService service;

    @Autowired
    public RegisterController(RegisterService service) {
        this.service = service;
    }

    @GetMapping("/register")
    public String registerGet() {
        return REGISTER.getCropURL();
    }

    //TODO: don't forget to add full commit info
    @PostMapping("/register")
    public String registerPost(@RequestParam(name = "name") String name,
                               @RequestParam(name = "email") String email,
                               @RequestParam(name="password") String pass) {

        return service.register(name, email, pass);
    }
}
