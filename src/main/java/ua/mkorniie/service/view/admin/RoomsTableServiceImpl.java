package ua.mkorniie.service.view.admin;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ua.mkorniie.dao.RoomRepository;
import ua.mkorniie.model.enums.RoomClass;
import ua.mkorniie.model.pojo.Room;

import java.util.Optional;

import static ua.mkorniie.service.util.StringConverter.parseLong;

@Slf4j
@Controller
public class RoomsTableServiceImpl implements RoomsTableService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomsTableServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void delete(@NonNull String idString) {
        Optional<Long> id = parseLong(idString);
        if (id.isPresent()) {
            Optional<Room> room = roomRepository.findById(id.get());
            room.ifPresent(roomRepository::delete);
        }
    }

    @Override
    public void save(@NonNull String pictureURL, @NonNull Integer places, @NonNull RoomClass roomClass,
                     @NonNull Double price) {
        roomRepository.save(new Room(places, roomClass, pictureURL, price));
    }

    @Override
    public void paginate(@NonNull Model model, @NonNull Pageable pageable) {
        model.addAttribute("page", roomRepository.findAll(pageable));
    }
}
