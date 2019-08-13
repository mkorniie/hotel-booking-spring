package ua.mkorniie.controller.service.view.admin;

import org.springframework.ui.Model;
import ua.mkorniie.model.enums.Language;
import ua.mkorniie.model.enums.Role;

import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

public interface UsersTableService {

    void newUser(@NotNull String name, @NotNull String email, @NotNull String pass,
                 @NotNull Role role, @NotNull Language language);

    void delete(@NotNull String id);

    void changePrivilege(@NotNull String id, String method);

    String getUsers(@NotNull Model model, @NotNull Pageable pageable);
}
