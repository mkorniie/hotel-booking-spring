package ua.mkorniie.service.view.user;

import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

public interface UserRequestsService {
    void paginate(@NonNull Model model, @NonNull Pageable pageable);

    void cancel(@NonNull String requestId);

    void newRequest(@NonNull String places, @NonNull String clazz, @NonNull String daterange);
}
