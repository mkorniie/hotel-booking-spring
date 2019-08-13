package ua.mkorniie.controller.service.view.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ua.mkorniie.controller.dao.UserRepository;
import ua.mkorniie.model.enums.Language;
import ua.mkorniie.model.enums.Role;
import ua.mkorniie.model.pojo.User;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static ua.mkorniie.model.util.StringConverter.parseLong;
import static ua.mkorniie.model.util.directions.Pages.ADMIN_USERS_PAGE;

@Slf4j
@Service
public class UserTableServiceImpl implements UsersTableService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserTableServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void newUser(@NotNull String name, @NotNull String email, @NotNull String pass, @NotNull Role role,
                        @NotNull Language language) {
        try {
            userRepository.save( new User.Builder()
                    .withName(name)
                    .withRole(role)
                    .withPasswordEncoded(encoder.encode(pass))
                    .withEmail(email)
                    .withLanguage(language)
                    .build());
        } catch (Exception e) {
            log.error("Impossible to create User at admin/users: " +
                    e.getMessage());
        }
    }

    @Override
    public void delete(@NotNull String idString) {
        Long id = parseLong(idString);
        if (id != null) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public void changePrivilege(@NotNull String idString, String method) {
        Long id = parseLong(idString);
        if (id != null) {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setRole(method.equals("privilege_a") ? Role.ADMIN : Role.USER);
                userRepository.save(user);
            }
        }
    }

    @Override
    public String getUsers(@NotNull Model model, @NotNull Pageable pageable) {
        model.addAttribute("page", userRepository.findAll(pageable));
        return ADMIN_USERS_PAGE.getCropPath();
    }
}
