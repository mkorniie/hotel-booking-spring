package ua.mkorniie.controller.service.view.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ua.mkorniie.controller.dao.RoomRepository;
import ua.mkorniie.model.enums.RoomClass;
import ua.mkorniie.model.pojo.Room;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static ua.mkorniie.model.util.StringConverter.parseLong;

@Slf4j
@Controller
public class RoomsTableServiceImpl implements RoomsTableService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomsTableServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void delete(@NotNull String idString) {
        Long id = parseLong(idString);
        if (id != null) {
            Optional<Room> room = roomRepository.findById(id);
            room.ifPresent(roomRepository::delete);
        }
    }

    @Override
    public void save(@NotNull String pictureURL, @NotNull Integer places, @NotNull RoomClass roomClass,
                     @NotNull Double price) {
        roomRepository.save(new Room(places, roomClass, pictureURL, price));
    }

    @Override
    public void paginate(@NotNull Model model, @NotNull Pageable pageable) {
        model.addAttribute("page", roomRepository.findAll(pageable));
    }
}
