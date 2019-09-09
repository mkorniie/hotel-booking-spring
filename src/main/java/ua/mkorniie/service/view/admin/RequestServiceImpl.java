package ua.mkorniie.service.view.admin;

import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ua.mkorniie.dao.BillRepository;
import ua.mkorniie.dao.RequestRepository;
import ua.mkorniie.dao.RoomRepository;
import ua.mkorniie.model.enums.Status;
import ua.mkorniie.model.pojo.Bill;
import ua.mkorniie.model.pojo.Request;
import ua.mkorniie.model.pojo.Room;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ua.mkorniie.service.util.directions.Pages.ADMIN_MAIN_PAGE;
import static ua.mkorniie.service.util.directions.Pages.ADMIN_REQUEST_APPROVE_PAGE;

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
    public boolean approve(@NonNull Long requestId, @NonNull Long roomId) {
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
    public void cancel(@NonNull String id) {
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
    public Request validSelected(@NonNull String id) {
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
    public String getRequests(@NonNull Model model, @NonNull Pageable pageable) {
        Page<Request> page = requestRepository.findAll(pageable);
        model.addAttribute("page", page);
        return ADMIN_MAIN_PAGE.getCropPath();
    }


    @Override
    public String showApprove(@NonNull Pageable pageable, @NonNull Model model, @NonNull Request selected) {

        model.addAttribute("selected_request", selected);

        List<Room> content = findMatchingRooms(selected);
        Page<Room> page = new PageImpl<>(content);
        model.addAttribute("page", page);

        return ADMIN_REQUEST_APPROVE_PAGE.getCropPath();
    }

    private boolean withinDateRange(@NonNull Room r, @NonNull Request selected) {
        List<Bill> bills = Lists.newArrayList(billRepository.findAll());

        for (Bill bill : bills) {
            if (Objects.equals(bill.getRoom().getId(), r.getId())) {
                if (datesOverlap(bill, selected)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean datesOverlap(@NonNull Bill b, @NonNull Request selected) {

        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");

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

    private List<Room> findMatchingRooms(@NonNull Request selected) {
        List<Room> matching = new ArrayList<>();

        List<Room> allRooms =  Lists.newArrayList(roomRepository.findAll());

        for (Room room : allRooms) {
            if (room.getRoomClass() == selected.getRoomClass()) {
                if (room.getPlaces() >= selected.getPlaces()) {
                    if (withinDateRange(room, selected)) {
                        matching.add(room);
                    }
                }
            }

        }
        return matching;
    }
}
