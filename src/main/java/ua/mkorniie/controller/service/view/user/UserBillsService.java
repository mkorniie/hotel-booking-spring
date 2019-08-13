package ua.mkorniie.controller.service.view.user;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import javax.validation.constraints.NotNull;

public interface UserBillsService {
    void pay(@NotNull String billId);

    void paginate(@NotNull Model model, @NotNull Pageable pageable);

    void cancel(@NotNull String requestId);
}
