package ua.mkorniie.controller.service.view.admin;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ua.mkorniie.controller.dao.BillRepository;
import ua.mkorniie.controller.dao.RequestRepository;
import ua.mkorniie.controller.dao.RoomRepository;
import ua.mkorniie.model.enums.Status;
import ua.mkorniie.model.pojo.Bill;
import ua.mkorniie.model.pojo.Request;
import ua.mkorniie.model.pojo.Room;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ua.mkorniie.model.util.directions.Pages.ADMIN_MAIN_PAGE;
import static ua.mkorniie.model.util.directions.Pages.ADMIN_REQUEST_APPROVE_PAGE;

@Slf4j
@Service
public class RequestServiceImpl implements RequestService {
    private final RoomRepository roomRepository;
    private final RequestRepository requestRepository;
    private final BillRepository billRepository;

    public RequestServiceImpl(RoomRepository roomRepository, RequestRepository requestRepository, BillRepository billRepository) {
        this.roomRepository = roomRepository;
        this.requestRepository = requestRepository;
        this.billRepository = billRepository;
    }

    @Override
    public boolean approve(@NotNull Long requestId, @NotNull Long roomId) {
        Optional<Room> selectedRoomOp = Optional.empty();
        Optional<Request> relatedRequestOp = Optional.empty();
        boolean result;
        try {
            selectedRoomOp = roomRepository.findById(roomId);
            relatedRequestOp = requestRepository.findById(requestId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (selectedRoomOp.isPresent() && relatedRequestOp.isPresent()) {
            Room selectedRoom = selectedRoomOp.get();
            Request relatedRequest = relatedRequestOp.get();

            relatedRequest.setStatus(Status.approved);
            requestRepository.save(relatedRequest);

            Bill bill = new Bill(selectedRoom.getPrice(), false, relatedRequest, selectedRoom);
            billRepository.save(bill);
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    @Override
    public void cancel(@NotNull String id) {
        try {
            Optional<Request> selectedRequestOptional = requestRepository.findById(Long.valueOf(id));
            if (selectedRequestOptional.isPresent()) {
                Request selected = selectedRequestOptional.get();
                selected.setStatus(Status.cancelled);
                requestRepository.save(selected);
            }
        } catch (NumberFormatException e) {
            log.error("Impossible to cancel request with id " + id  + "\n" +
                    "Error thrown: " + e.getMessage());
        }
    }

    @Override
    public Request validSelected(@NotNull String id) {
        Request selected = null;
        try {
            Optional<Request> selectedRequestOptional = requestRepository.findById(Long.valueOf(id));
            if (selectedRequestOptional.isPresent()) {
                selected = selectedRequestOptional.get();
            }
        } catch (NumberFormatException e) {
            log.error("Impossible to select request with id " + id  + "\n" +
                    "Error thrown: " + e.getMessage());
        }
        return selected;
    }

    @Override
    public String getRequests(@NotNull Model model, @NotNull Pageable pageable) {
        Page<Request> page = requestRepository.findAll(pageable);
        model.addAttribute("page", page);
        return ADMIN_MAIN_PAGE.getCropPath();
    }


    @Override
    public String showApprove(@NotNull Model model, @NotNull Request selected) {

        model.addAttribute("selected_request", selected);

        List<Room> matchingRooms = findMatchingRooms(selected);
        model.addAttribute("entries", matchingRooms);

        return ADMIN_REQUEST_APPROVE_PAGE.getCropPath();
    }

    private boolean withinDateRange(@NotNull Room r, @NotNull Request selected) {
        List<Bill> bills = Lists.newArrayList(billRepository.findAll());

        for (Bill b : bills) {
            if (Objects.equals(b.getRoom().getId(), r.getId())) {
                if (datesOverlap(b, selected)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean datesOverlap(@NotNull Bill b, @NotNull Request selected) {

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

        List<Room> allRooms =  Lists.newArrayList(roomRepository.findAll());

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
}
