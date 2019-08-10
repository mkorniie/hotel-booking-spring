package ua.mkorniie.controller.controller.admin;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mkorniie.controller.dao.BillRepository;
import ua.mkorniie.controller.dao.RequestRepository;
import ua.mkorniie.controller.dao.RoomRepository;
import ua.mkorniie.controller.dao.UserRepository;
import ua.mkorniie.model.enums.Language;
import ua.mkorniie.model.enums.Role;
import ua.mkorniie.model.enums.RoomClass;
import ua.mkorniie.model.pojo.Bill;
import ua.mkorniie.model.pojo.Request;
import ua.mkorniie.model.pojo.Room;
import ua.mkorniie.model.pojo.User;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ua.mkorniie.model.util.directions.Pages.*;

//TODO: make "repeat your password"?
//TODO: add Slf4j everywhere
// TODO: make limitations on username (6-32?)
//  TODO: !!!UNIQUE username!!! Erorr "username already exists"
// TODO: password size validation
@Slf4j
@Controller
@RequestMapping("/admin/*")
public class AdminController {
    private final RequestRepository requestDAO;
    private final BillRepository billDAO;
    private final UserRepository userDAO;
    private final RoomRepository roomDAO;
    private List<RoomClass> classes = Arrays.asList(RoomClass.values());
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    AdminController(RequestRepository requestDAO, BillRepository billDAO, UserRepository userDAO, RoomRepository roomDAO) {
        this.requestDAO = requestDAO;
        this.billDAO = billDAO;
        this.userDAO = userDAO;
        this.roomDAO = roomDAO;
    }

    @GetMapping("/admin/")
    public String getMain(@RequestParam(name ="method", required = false) String method,
            @RequestParam(name = "id", required = false) String id,
            Model model) {

        if(method != null && id != null && method.equals("approve")) {
            try {
                Optional<Request> selectedRequestOptional = requestDAO.findById(Long.valueOf(id));
                if (selectedRequestOptional.isPresent()) {
                    Request selected = selectedRequestOptional.get();
                    showApprove(model, selected);
                }
            } catch (NumberFormatException e) {
            }
        }


        model.addAttribute("entries", Lists.newArrayList(requestDAO.findAll()).stream()
                                                        .filter(r -> !r.isApproved())
                                                        .collect(Collectors.toList()));
        return ADMIN_MAIN_PAGE.getCropURL();
    }

    @GetMapping("/admin/users")
    public String getUsers(Model model,
                           @RequestParam(name = "method", required = false) String method,
                           @RequestParam(name = "id", required = false) Long id) {
        if(method != null && id != null) {
            if (method.equals("remove"))
                userDAO.deleteById(id);
            else if (method.equals("priviledge_a") || method.equals("priviledge_u")){
                Optional<User> optionalUser = userDAO.findById(id);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    user.setRole(method.equals("priviledge_a") ? Role.ADMIN : Role.USER);
                    userDAO.save(user);
                }
            }
        }

        model.addAttribute("entries", userDAO.findAll());
        return ADMIN_USERS_PAGE.getCropURL();
    }

    @GetMapping("/admin/tables")
    public String getTables(Model model) {
        model.addAttribute("entries_bills", billDAO.findAll());
        model.addAttribute("entries_rooms", roomDAO.findAll());
        return ADMIN_TABLES_PAGE.getCropURL();
    }

//    @RequestMapping(name = "/admin/approve", method = RequestMethod.POST)
    public String showApprove(@NotNull Model model, @NotNull Request selected) {

        model.addAttribute("selected-request", selected);

        List<Room> matchingRooms = findMatchingRooms(selected);
        model.addAttribute("entries", matchingRooms);

//        Pagination<Room> roomPagination = new Pagination();
//        roomPagination.paginate(matchingRooms, request, response);
        return ADMIN_REQUESTAPPROVE_PAGE.getCropURL();
//        request.getRequestDispatcher(Paths.APPROVE_ROOM_REQ.getUrl()).forward(request, response);
    }

    private boolean withinDateRange(@com.sun.istack.internal.NotNull Room r, @com.sun.istack.internal.NotNull Request selected) {
        List<Bill> bills = Lists.newArrayList(billDAO.findAll());

        for (Bill b : bills) {
            if (b.getRoom().getId() == r.getId()) {
                if (datesOverlap(b, selected)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean datesOverlap(@com.sun.istack.internal.NotNull Bill b, @com.sun.istack.internal.NotNull Request selected) {
        Date selectedStart = Date.valueOf(selected.getStartDate());
        Date selectedEnd = Date.valueOf(selected.getEndDate());

        Date reqStart = Date.valueOf(b.getRequest().getStartDate());
        Date reqEnd = Date.valueOf(b.getRequest().getEndDate());

        if (selectedStart.after(reqEnd) || reqStart.after(selectedEnd)) {
            return false;
        }
        return true;
    }

    private List<Room> findMatchingRooms(@NotNull Request selected) {
        List<Room> matching = new ArrayList<>();

        List<Room> allRooms =  Lists.newArrayList(roomDAO.findAll());

        for (Room r : allRooms) {
            if (r.getRoomClass() == selected.getRoomClass()) {
                if (r.getPlaces() >= selected.getPlaces()) {
                    if (withinDateRange(r, selected)) {
                        matching.add(r);
                    }
                }
            }

        }
        return matching;
    }

    @PostMapping("/admin/users-update")
    public String userUpdatePost(Model model, @RequestParam(name = "name") String name,
                               @RequestParam(name = "mail") String email,
                               @RequestParam(name="pass") String pass,
                                 @RequestParam(name="role") Role role,
                                 @RequestParam(name="lang") Language language) {
        try {
            userDAO.save( new User.Builder()
                    .withName(name)
                    .withRole(role)
                    .withPasswordEncoded(encoder.encode(pass))
                    .withEmail(email)
                    .withLanguage(language)
                    .build());
        } catch (Exception e) {
            log.error("Impossible to create User at admin/users: " +
                    e.getMessage());
        }
        return getUsers(model, null, null);
    }

    @PostMapping("/admin/update-rooms")
    public String tablesAddRoom(Model model,
                                @RequestParam("picture") String pictureURL,
                                @RequestParam("places") Integer places,
                                @RequestParam("roomClass") RoomClass roomClass,
                                @RequestParam("price") Double price) {
        roomDAO.save(new Room(places, roomClass, pictureURL, price));
        return getTables(model);
    }
}

