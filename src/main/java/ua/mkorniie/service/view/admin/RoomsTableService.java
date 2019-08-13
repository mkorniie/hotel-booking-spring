package ua.mkorniie.service.view.admin;

import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import ua.mkorniie.model.enums.RoomClass;

public interface RoomsTableService {
    void delete(@NonNull String id);

    void save(@NonNull String pictureURL, @NonNull Integer places, @NonNull RoomClass roomClass,
              @NonNull Double price);

    void paginate(@NonNull Model model, @NonNull Pageable pageable);
}
