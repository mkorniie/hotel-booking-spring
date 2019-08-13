package ua.mkorniie.service.view.anonymous;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import static ua.mkorniie.service.util.directions.Pathes.ADMIN_MAIN;
import static ua.mkorniie.service.util.directions.Pathes.USER_MAIN;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    @Override
    public String redirectIfAuthenticated(Authentication authentication) {
        if (authentication != null) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
                return "redirect:" + USER_MAIN.getUrl();
            } else {
                return "redirect:" + ADMIN_MAIN.getUrl();
            }
        }
        return null;
    }
}
