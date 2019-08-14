package ua.mkorniie.service.view.user;

import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import ua.mkorniie.service.security.HotelUserDetails;

public interface UserRequestsService {
    void paginate(@NonNull HotelUserDetails principal, @NonNull Model model, @NonNull Pageable pageable);

    void cancel(@NonNull String requestId);

    void newRequest(@NonNull HotelUserDetails principal, @NonNull String places, @NonNull String clazz, @NonNull String daterange);
}
