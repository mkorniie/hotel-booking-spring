package ua.mkorniie.service.view.anonymous;

import lombok.NonNull;
import org.springframework.security.core.Authentication;

public interface LoginService {
    String login(Authentication authentication, @NonNull String username, @NonNull String password);
}
