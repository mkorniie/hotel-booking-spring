package ua.mkorniie.controller.service.view.user;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import javax.validation.constraints.NotNull;

public interface UserRequestsService {
    void paginate(@NotNull Model model, @NotNull Pageable pageable);

    void cancel(@NotNull String requestId);

    void newRequest(@NotNull String places, @NotNull String clazz, @NotNull String daterange);
}
