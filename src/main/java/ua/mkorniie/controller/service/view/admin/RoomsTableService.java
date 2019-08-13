package ua.mkorniie.controller.service.view.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import ua.mkorniie.model.enums.RoomClass;

import javax.validation.constraints.NotNull;

public interface RoomsTableService {
    void delete(@NotNull String id);

    void save(@NotNull String pictureURL, @NotNull Integer places, @NotNull RoomClass roomClass,
              @NotNull Double price);

    void paginate(@NotNull Model model, @NotNull Pageable pageable);
}
