package ua.mkorniie.service.view.user;

import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

public interface UserBillsService {
    void pay(@NonNull String billId);

    void paginate(@NonNull Model model, @NonNull Pageable pageable);

    void cancel(@NonNull String requestId);
}
