package ua.mkorniie.controller.controller.admin;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.mkorniie.controller.dao.BillRepository;
import ua.mkorniie.controller.dao.RequestRepository;
import ua.mkorniie.controller.dao.RoomRepository;
import ua.mkorniie.controller.dao.UserRepository;
import ua.mkorniie.model.enums.Language;
import ua.mkorniie.model.enums.Role;
import ua.mkorniie.model.enums.RoomClass;
import ua.mkorniie.model.enums.Status;
import ua.mkorniie.model.pojo.Bill;
import ua.mkorniie.model.pojo.Request;
import ua.mkorniie.model.pojo.Room;
import ua.mkorniie.model.pojo.User;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    public String getMain(Model model,
                            @RequestParam(name ="method", required = false) String method,
                            @RequestParam(name = "id", required = false) String id,
                            @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        if(method != null && id != null) {
            if (method.equals("approve")) {
                try {
                    Optional<Request> selectedRequestOptional = requestDAO.findById(Long.valueOf(id));
                    if (selectedRequestOptional.isPresent()) {
                        Request selected = selectedRequestOptional.get();
                        return showApprove(model, selected);
                    }
                } catch (NumberFormatException e) {
                }
            } else if (method.equals("cancel")) {
                try {
                    Optional<Request> selectedRequestOptional = requestDAO.findById(Long.valueOf(id));
                    if (selectedRequestOptional.isPresent()) {
                        Request selected = selectedRequestOptional.get();
                        selected.setStatus(Status.cancelled);
                        requestDAO.save(selected);
                    }
                } catch (NumberFormatException e) {
                }
            }

        }

        Page<Request> page = requestDAO.findAll(pageable);
        model.addAttribute("page", page);
//        model.addAttribute("page", Lists.newArrayList(requestDAO.findAll());
        return ADMIN_MAIN_PAGE.getCropURL();
    }

    @GetMapping("/admin/users")
    public String getUsers(Model model,
                           @RequestParam(name = "method", required = false) String method,
                           @RequestParam(name = "id", required = false) Long id,
                           @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable ) {
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
        model.addAttribute("page", userDAO.findAll(pageable));
        return ADMIN_USERS_PAGE.getCropURL();
    }

    @GetMapping("/admin/bills")
    public String getTBills(Model model,
                            @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        model.addAttribute("page", billDAO.findAll(pageable));
        return ADMIN_BILLS_PAGE.getCropURL();
    }

    @GetMapping("/admin/rooms")
    public String getRooms(Model model,
                            @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        model.addAttribute("page", roomDAO.findAll(pageable));
        return ADMIN_ROOMS_PAGE.getCropURL();
    }

    public String showApprove(@NotNull Model model, @NotNull Request selected) {

        model.addAttribute("selected_request", selected);

        List<Room> matchingRooms = findMatchingRooms(selected);
        model.addAttribute("entries", matchingRooms);

//        Pagination<Room> roomPagination = new Pagination();
//        roomPagination.paginate(matchingRooms, request, response);
        return ADMIN_REQUESTAPPROVE_PAGE.getCropURL();
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

        SimpleDateFormat sdf1 = new SimpleDateFormat("mm/dd/yyyy");

        try {
            Date selectedStart = new Date(sdf1.parse(selected.getStartDate()).getTime());
            Date selectedEnd = new Date(sdf1.parse(selected.getEndDate()).getTime());
            Date reqStart = new Date(sdf1.parse(b.getRequest().getStartDate()).getTime());
            Date reqEnd = new Date(sdf1.parse(b.getRequest().getEndDate()).getTime());

            return !(selectedStart.after(reqEnd) || reqStart.after(selectedEnd));
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        return false;
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
                                 @RequestParam(name="lang") Language language,
                                 @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
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
        return getUsers(model, null, null, pageable);
    }

    @PostMapping("/admin/update-rooms")
    public String tablesAddRoom(Model model,
                                @RequestParam("picture") String pictureURL,
                                @RequestParam("places") Integer places,
                                @RequestParam("roomClass") RoomClass roomClass,
                                @RequestParam("price") Double price,
                                @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        roomDAO.save(new Room(places, roomClass, pictureURL, price));
        return getRooms(model, pageable);
    }

    //TODO: add not null annotation
    @PostMapping("/admin/approve")
    public String approveRequest(Model model,
                                    @RequestParam("id") Long requestId,
                                    @RequestParam("room-select") Long roomId,
                                    @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {

        Optional<Room> selectedRoomOp = Optional.empty();
        Optional<Request> relatedRequestOp = Optional.empty();
        try {
            selectedRoomOp = roomDAO.findById(roomId);
            relatedRequestOp = requestDAO.findById(requestId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (selectedRoomOp.isPresent() && relatedRequestOp.isPresent()) {
            Room selectedRoom = selectedRoomOp.get();
            Request relatedRequest = relatedRequestOp.get();

            relatedRequest.setStatus(Status.approved);
            requestDAO.save(relatedRequest);

            Bill bill = new Bill(selectedRoom.getPrice(), false, relatedRequest, selectedRoom);
            billDAO.save(bill);
        }
        else {
            model.addAttribute("method", "approve");
            model.addAttribute("id", requestId);
        }
        return getMain(model, null, null, pageable);
    }

}

