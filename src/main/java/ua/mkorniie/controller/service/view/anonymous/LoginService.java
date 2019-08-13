package ua.mkorniie.controller.service.view.anonymous;

import javax.validation.constraints.NotNull;

public interface LoginService {
    String login(@NotNull String username, @NotNull String password);
}
