package ua.mkorniie.controller.service.view.anonymous;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ua.mkorniie.controller.dao.UserRepository;
import ua.mkorniie.model.enums.Role;
import ua.mkorniie.model.pojo.User;
import ua.mkorniie.model.util.directions.Pathes;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Locale;

import static ua.mkorniie.model.util.directions.Pages.GENERAL_ERROR;


@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    //TODO: check once again - the order and the return (GENERAL_ERROR.getCropPath();? I have message)
    @Override
    public String login(@NotNull String username, @NotNull String password) {
        User u = null;
        try {
            u = userRepository.findByName(username);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (u == null) {
            return GENERAL_ERROR.getCropPath();
        } else if (encoder.matches(password, u.getPasswordEncoded())) {
            try {
                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                HttpSession session = attr.getRequest().getSession(true);
                session.setAttribute("user", u);
                LocaleContextHolder.setLocale(new Locale(u.getLanguage().name()));
                if (u.getRole() == Role.USER) {
                    return "redirect:" + Pathes.USER_MAIN.getUrl();
                } else if (u.getRole() == Role.ADMIN) {
                    return "redirect:" + Pathes.ADMIN_MAIN.getUrl();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        //TODO:change once security
        return "redirect:" + Pathes.LOGIN.getUrl();
    }
}
