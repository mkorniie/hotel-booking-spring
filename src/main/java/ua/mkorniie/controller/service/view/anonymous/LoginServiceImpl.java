package ua.mkorniie.controller.service.view.anonymous;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ua.mkorniie.controller.dao.UserRepository;
import ua.mkorniie.model.enums.Role;
import ua.mkorniie.model.pojo.User;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Locale;

import static ua.mkorniie.model.util.directions.Pages.*;
import static ua.mkorniie.model.util.directions.Pages.LOGIN;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public LoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //TODO: check once again - the order and the return (GENERAL_ERROR.getCropURL();? I have message)
    @Override
    public String login(@NotNull String username, @NotNull String password) {
        User u = null;
        try {
            u = userRepository.findByName(username);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (u == null) {
            return GENERAL_ERROR.getCropURL();
        } else if (encoder.matches(password, u.getPasswordEncoded())) {
            try {
                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                HttpSession session = attr.getRequest().getSession(true);
                session.setAttribute("user", u);
                LocaleContextHolder.setLocale(new Locale(u.getLanguage().name()));
                if (u.getRole() == Role.USER) {
                    return USER_MAIN_PAGE.getCropURL();
                } else if (u.getRole() == Role.ADMIN) {
                    return ADMIN_MAIN_PAGE.getCropURL();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return LOGIN.getCropURL();
    }
}
