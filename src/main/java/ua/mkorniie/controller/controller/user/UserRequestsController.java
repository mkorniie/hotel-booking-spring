package ua.mkorniie.controller.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.mkorniie.controller.service.view.user.UserRequestsService;
import ua.mkorniie.model.util.directions.Pages;
import ua.mkorniie.model.util.directions.Pathes;


//TODO: see if Sl4j is used only in Controller???
//TODO: both servlet and spring - make logging settings (see: https://youtu.be/iivY8B5A0Tk?t=716)
//TODO: add the annotation everywhere and log properly
//TODO: move pathes everywhere to Service - same as admin??? or all to controller?
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
        return "user/user-requests";
    }

    @PostMapping("/user/make-request")
    public String makeRequest(@RequestParam("places") String pl,
                              @RequestParam("class") String clazz,
                              @RequestParam("daterange") String daterange) {
        log.info("Retrieving data from request...");
        service.newRequest(pl, clazz, daterange);

        //TODO: do a redirect: redirect:/user/my-requests ?
        return "redirect:" + Pathes.USER_MAIN.getUrl();
    }

}