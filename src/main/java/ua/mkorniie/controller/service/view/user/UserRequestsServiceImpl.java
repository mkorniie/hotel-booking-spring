package ua.mkorniie.controller.service.view.user;

import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ua.mkorniie.controller.dao.BillRepository;
import ua.mkorniie.controller.dao.RequestRepository;
import ua.mkorniie.controller.dao.UserRepository;
import ua.mkorniie.model.enums.RoomClass;
import ua.mkorniie.model.enums.Status;
import ua.mkorniie.model.exceptions.DateFormatException;
import ua.mkorniie.model.pojo.Bill;
import ua.mkorniie.model.pojo.Request;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserRequestsServiceImpl implements UserRequestsService{
    private final RequestRepository requestRepository;
    private final BillRepository billRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserRequestsServiceImpl(@NotNull RequestRepository requestDAO, @NotNull BillRepository billDAO, UserRepository userRepository) {
        this.requestRepository = requestDAO;
        this.billRepository = billDAO;
        this.userRepository = userRepository;
    }

    //TODO: what happens if nothing is found??
    @Override
    public void paginate(@NotNull Model model, @NotNull Pageable pageable) {
        Page<Request> page = requestRepository.findAll(pageable);
        model.addAttribute("page", page);
    }

    @Override
    public void cancel(@NotNull String requestId) {
        Long id = null;
        try {
            id = Long.parseLong(requestId);
        } catch (Exception e) {
            log.error("Impossible to parse request id: " + requestId + "\n"
                    + e.getMessage());
        }
        Bill relatedBill = billRepository.findByRequestId(Long.valueOf(id));
        if (relatedBill != null) {
            billRepository.delete(relatedBill);
        }
        requestRepository.deleteById(Long.valueOf(id));
        }

    @Override
    public void newRequest(@NotNull String placesString, @NotNull String clazz, @NotNull String daterange) {
        try {
            int places = Integer.parseInt(placesString);
            RoomClass roomClass = RoomClass.valueOf(clazz);
            List<String> dates = parseDates(daterange);
            log.info("Dates: " + dates);

            //TODO: change to current user!!
            Request newRequest = new Request(userRepository.findById(2L).get(),
                    places,
                    roomClass,
                    dates.get(0), dates.get(1),
                    Status.waitingForApproval);
            requestRepository.save(newRequest);
        } catch (Exception e) {
            log.error("Impossible to create request object: wrong input format;");
            log.error(e.getMessage());
        }
    }

    private List<String> parseDates(@NotNull String daterange) throws DateFormatException, ParseException {
        List<String> result = new ArrayList<>();
        String[] dates = daterange.split(" - ");

        if (dates.length != 2) {
            throw new DateFormatException("Error parsing date string: " + daterange);
        }

        for (String s : dates) {
            formatTester(s);
            result.add(s);
        }
        return result;
    }

    private void formatTester(@NotNull String s) throws ParseException {
        new SimpleDateFormat("mm/dd/yyyy").parse(s);
    }
}
