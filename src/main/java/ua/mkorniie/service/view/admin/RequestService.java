package ua.mkorniie.service.view.admin;

import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import ua.mkorniie.model.pojo.Request;

public interface RequestService {
    boolean approve(@NonNull Long requestId, @NonNull Long roomId);

    void cancel(@NonNull String id);

    Request validSelected(@NonNull String id);

    String getRequests(@NonNull Model model, @NonNull Pageable pageable);

    String showApprove(@NonNull Model model, @NonNull Request selected);
}
