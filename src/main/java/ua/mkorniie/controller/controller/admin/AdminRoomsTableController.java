package ua.mkorniie.controller.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mkorniie.controller.service.view.admin.RoomsTableService;
import ua.mkorniie.model.enums.RoomClass;

import static ua.mkorniie.model.util.directions.Pages.ADMIN_ROOMS_PAGE;

@Slf4j
@Controller
public class AdminRoomsTableController {
    private final RoomsTableService service;

    @Autowired
    public AdminRoomsTableController(RoomsTableService service) {
        this.service = service;
    }

    @GetMapping("/admin/rooms")
    public String getRooms(Model model,
                           @RequestParam(name = "method", required = false) String method,
                           @RequestParam(name = "id", required = false) String id,
                           @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        if (method != null && id != null && method.equals("delete")) {
            service.delete(id);
        }
        service.paginate(model, pageable);
        return ADMIN_ROOMS_PAGE.getCropURL();
    }

    @PostMapping("/admin/update-rooms")
    public String tablesAddRoom(Model model,
                                @RequestParam("picture") String pictureURL,
                                @RequestParam("places") Integer places,
                                @RequestParam("roomClass") RoomClass roomClass,
                                @RequestParam("price") Double price,
                                @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {

        service.save(pictureURL, places, roomClass, price);
        return getRooms(model, null, null, pageable);
    }
}
