package ua.mkorniie.controller.controller.user;

import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.mkorniie.controller.dao.BillRepository;
import ua.mkorniie.controller.dao.RequestRepository;
import ua.mkorniie.controller.dao.UserRepository;
import ua.mkorniie.model.enums.RoomClass;
import ua.mkorniie.model.enums.Status;
import ua.mkorniie.model.exceptions.DateFormatException;
import ua.mkorniie.model.pojo.Bill;
import ua.mkorniie.model.pojo.Request;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO: see if Sl4j is used only in Controller???
//TODO: both servlet and spring - make logging settings (see: https://youtu.be/iivY8B5A0Tk?t=716)
//TODO: add the annotation everywhere and log properly
@Slf4j
@Controller
@RequestMapping("/user/*")
public class UserController {
    private final RequestRepository requestDAO;
    private final BillRepository billDAO;
    private final UserRepository userDAO;
    private List<RoomClass> classes = Arrays.asList(RoomClass.values());

    UserController(RequestRepository requestDAO, BillRepository billDAO, UserRepository userDAO) {
        this.requestDAO = requestDAO;
        this.billDAO = billDAO;
        this.userDAO = userDAO;
    }




    @GetMapping("/user/my-bills")
    public String getBills(@RequestParam(value = "method", required = false) String method,
                           @RequestParam(value = "req_id", required = false) String requestId,
                           @RequestParam(value = "id", required = false) String billId,
                           @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
                           Model model) {

        if (method != null && requestId != null) {
            if (method.equals("cancel")) {
                log.info("Attempting to delete Bill with id=" + requestId + " and corresponding Request");
                try {
                    Bill bill = billDAO.findById(Long.parseLong(requestId)).get();
                    Request request = bill.getRequest();
                    billDAO.delete(bill);
                    requestDAO.delete(request);
                    log.info("Success");
                } catch (Exception e) {
                    log.error("Failure: Bill with id=" + requestId + " hasn't been deleted");
                }
            }
        }
        if (method != null && billId != null) {
            if (method.equals("pay")) {
                log.info("Attempting to pay Bill with id=" + billId);
                try {
                    Bill bill = billDAO.findById(Long.parseLong(billId)).get();
                    bill.setPaid(true);
                    billDAO.save(bill);
                    log.info("Success");
                } catch (Exception e) {
                    log.error("Failure: Bill with id=" + billId + " hasn't been paid");
                }
            }
        }

        Page<Bill> page = billDAO.findAll(pageable);
        model.addAttribute("page", page);
        return "user/user-bills";
    }

    private void cancelRequest(@NotNull int id) {
        Bill relatedBill = billDAO.findByRequestId(Long.valueOf(id));
        if (relatedBill != null) {
            billDAO.delete(relatedBill);
        }
        requestDAO.deleteById(Long.valueOf(id));

    }

    @GetMapping("/user/my-requests")
    public String getRequests(@RequestParam(value = "method", required = false) String method,
                              @RequestParam(value = "req_id", required = false) String id,
                              @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
                              Model model) {
        //TODO: remember once there is security - only page corresponding to the user!

        if (method != null && id != null) {
            if (method.equals("cancel")) {
                cancelRequest(Integer.parseInt(id));
            }
        }
        Page<Request> page = requestDAO.findAll(pageable);
        model.addAttribute("page", page);
        return "user/user-requests";
    }

    @PostMapping("/user/make-request")
    public String makeRequest(@RequestParam("places") String pl,
                              @RequestParam("class") String clazz,
                              @RequestParam("daterange") String daterange,
                              Model model) {
        log.info("Retrieving data from request...");

        try {
            int places = Integer.parseInt(pl);
            RoomClass roomClass = RoomClass.valueOf(clazz);
            List<String> dates = parseDates(daterange);
            log.info("Dates: " + dates);

            //TODO: change to current user!!
            Request newRequest = new Request(userDAO.findById(2L).get(),
                    places,
                    roomClass,
                    dates.get(0), dates.get(1),
                    Status.waitingForApproval);
            requestDAO.save(newRequest);
        } catch (Exception e) {
            log.error("Impossible to create request object: wrong input format;");
            log.error(e.getMessage());
        }

        return getMain(model);
    }

    //TODO: check if date is properly formated!??? or not?
    private List<String> parseDates(@NotNull String daterange) throws DateFormatException, ParseException {
        List<String> result = new ArrayList<>();
        String[] dates = daterange.split(" - ");

        if (dates.length != 2) {
            throw new DateFormatException("Error parsing date string: " + daterange);
        }
        DateFormat df = new SimpleDateFormat("mm/dd/yyyy");

        for (String s : dates) {
            result.add(s);
        }
        return result;
    }

}