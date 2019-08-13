package ua.mkorniie.controller.controller.general;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ua.mkorniie.service.view.anonymous.AuthenticationService;

import static ua.mkorniie.service.util.directions.Pages.INDEX_PAGE;

@Slf4j
@Controller
public class MainController {
    private final AuthenticationService authenticationService;

    @Autowired
    public MainController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/")
    public String index(Authentication authentication) {
        String redirect = authenticationService.redirectIfAuthenticated(authentication);
        return (redirect != null) ? redirect : INDEX_PAGE.getCropPath();
    }
}
