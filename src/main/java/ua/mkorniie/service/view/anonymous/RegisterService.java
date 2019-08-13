package ua.mkorniie.service.view.anonymous;

import lombok.NonNull;

public interface RegisterService {
    String register(@NonNull String name, @NonNull String email, @NonNull String pass);
}
