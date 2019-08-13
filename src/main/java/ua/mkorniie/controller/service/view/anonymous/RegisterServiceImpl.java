package ua.mkorniie.controller.service.view.anonymous;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.mkorniie.controller.dao.UserRepository;
import ua.mkorniie.model.enums.Language;
import ua.mkorniie.model.enums.Role;
import ua.mkorniie.model.pojo.User;

import javax.validation.constraints.NotNull;
import java.util.Locale;

import static ua.mkorniie.model.util.directions.Pages.LOGIN_PAGE;
import static ua.mkorniie.model.util.directions.Pages.REGISTER_PAGE;

@Slf4j
@Service
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public RegisterServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String register(@NotNull String name, @NotNull String email, @NotNull String pass) {
        Locale locale = LocaleContextHolder.getLocale();

        User newUser = null;
        try {
            newUser = new User.Builder()
                    .withName(name)
                    .withRole(Role.USER)
                    .withPasswordEncoded(encoder.encode(pass))
                    .withEmail(email)
                    .withLanguage(Language.of(locale))
                    .build();
            userRepository.save(newUser);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return newUser == null ? REGISTER_PAGE.getCropURL() : LOGIN_PAGE.getCropURL();
    }
}
