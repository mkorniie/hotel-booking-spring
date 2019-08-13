package ua.mkorniie.service.view.admin;

import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import ua.mkorniie.model.enums.Language;
import ua.mkorniie.model.enums.Role;

public interface UsersTableService {

    void newUser(@NonNull String name, @NonNull String email, @NonNull String pass,
                 @NonNull Role role, @NonNull Language language);

    void delete(@NonNull String id);

    void changePrivilege(@NonNull String id, String method);

    String getUsers(@NonNull Model model, @NonNull Pageable pageable);
}
