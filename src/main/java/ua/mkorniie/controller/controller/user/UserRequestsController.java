package ua.mkorniie.controller.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mkorniie.service.security.HotelUserDetails;
import ua.mkorniie.service.util.directions.Pathes;
import ua.mkorniie.service.view.user.UserRequestsService;


@Slf4j
@Controller
public class UserRequestsController {
    private final UserRequestsService service;

    @Autowired
    public UserRequestsController(UserRequestsService service) { this.service = service; }



    @GetMapping("/user/my-requests")
    public String getRequests(@RequestParam(value = "method", required = false) String method,
                              @RequestParam(value = "req_id", required = false) String id,
                              @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
                              Model model) {
        //TODO: remember once there is security - only page corresponding to the user!

        if (method != null && id != null && method.equals("cancel")) {
                service.cancel(id);
        }

        service.paginate(model, pageable);
        return Pathes.USER_REQUESTS.getCropPagePath();
    }

    @PostMapping("/user/make-request")
    public String makeRequest(Authentication authentication,
                                @RequestParam("places") String pl,
                              @RequestParam("class") String clazz,
                              @RequestParam("daterange") String daterange) {
        log.info("Retrieving data from request...");
        if (authentication != null) {
            service.newRequest((HotelUserDetails)authentication.getPrincipal(), pl, clazz, daterange);
        } else {
            log.error("Authentication object ir null: unauthorized access!");
        }


        return "redirect:" + Pathes.USER_MAIN.getUrl();
    }

}