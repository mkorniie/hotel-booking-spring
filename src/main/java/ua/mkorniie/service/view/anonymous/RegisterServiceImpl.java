package ua.mkorniie.service.view.anonymous;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.mkorniie.dao.UserRepository;
import ua.mkorniie.model.enums.Language;
import ua.mkorniie.model.enums.Role;
import ua.mkorniie.model.pojo.User;

import java.util.Locale;

import static ua.mkorniie.service.util.directions.Pages.LOGIN_PAGE;
import static ua.mkorniie.service.util.directions.Pages.REGISTER_PAGE;

@Slf4j
@Service
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public RegisterServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public String register(@NonNull String name, @NonNull String email, @NonNull String pass) {
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
        return newUser == null ? REGISTER_PAGE.getCropPath() : LOGIN_PAGE.getCropPath();
    }
}
