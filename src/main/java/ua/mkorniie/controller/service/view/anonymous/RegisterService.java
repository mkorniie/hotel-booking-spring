package ua.mkorniie.controller.service.view.anonymous;

import javax.validation.constraints.NotNull;

public interface RegisterService {
    String register(@NotNull String name, @NotNull String email, @NotNull String pass);
}
