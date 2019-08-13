package ua.mkorniie.service.view.user;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ua.mkorniie.dao.BillRepository;
import ua.mkorniie.dao.RequestRepository;
import ua.mkorniie.dao.UserRepository;
import ua.mkorniie.model.enums.RoomClass;
import ua.mkorniie.model.enums.Status;
import ua.mkorniie.model.exceptions.DateFormatException;
import ua.mkorniie.model.pojo.Bill;
import ua.mkorniie.model.pojo.Request;
import ua.mkorniie.service.util.StringConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserRequestsServiceImpl implements UserRequestsService{
    private final RequestRepository requestRepository;
    private final BillRepository billRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserRequestsServiceImpl(@NonNull RequestRepository requestDAO, @NonNull BillRepository billDAO, UserRepository userRepository) {
        this.requestRepository = requestDAO;
        this.billRepository = billDAO;
        this.userRepository = userRepository;
    }

    //TODO: what happens if nothing is found??
    @Override
    public void paginate(@NonNull Model model, @NonNull Pageable pageable) {
        Page<Request> page = requestRepository.findAll(pageable);
        model.addAttribute("page", page);
    }

    @Override
    public void cancel(@NonNull String requestId) {
        Optional<Long> id = StringConverter.parseLong(requestId);
        if (id.isPresent()) {
            Bill relatedBill = billRepository.findByRequestId(id.get());
            if (relatedBill != null) {
                billRepository.delete(relatedBill);
            }
            requestRepository.deleteById(id.get());
        }
    }


    @Override
    public void newRequest(@NonNull String placesString, @NonNull String clazz, @NonNull String daterange) {
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

    private List<String> parseDates(@NonNull String daterange) throws DateFormatException, ParseException {
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

    private void formatTester(@NonNull String s) throws ParseException {
        new SimpleDateFormat("mm/dd/yyyy").parse(s);
    }
}
