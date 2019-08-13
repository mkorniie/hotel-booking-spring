package ua.mkorniie.controller.service.view.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import ua.mkorniie.model.pojo.Request;

import javax.validation.constraints.NotNull;

public interface RequestService {
    boolean approve(@NotNull Long requestId, @NotNull Long roomId);

    void cancel(@NotNull String id);

    Request validSelected(@NotNull String id);

    String getRequests(@NotNull Model model, @NotNull Pageable pageable);

    String showApprove(@NotNull Model model, @NotNull Request selected);
}
