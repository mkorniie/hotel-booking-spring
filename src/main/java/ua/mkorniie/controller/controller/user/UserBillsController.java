package ua.mkorniie.controller.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/user/my-bills")
public class UserBillsController {

    private final UserBillsService service;

    @Autowired
    public UserBillsController(UserBillsService service) {
        this.service = service;
    }


    @GetMapping("/user/my-bills")
    public String getBills(@RequestParam(value = "method", required = false) String method,
                           @RequestParam(value = "req_id", required = false) String requestId,
                           @RequestParam(value = "id", required = false) String billId,
                           @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
                           Model model) {

        if (method != null) {
            if (method.equals("cancel") && requestId != null) {
                service.cancel(requestId);
            } else if (method.equals("pay") && billId != null) {
                service.pay(billId);
            }
        }

        service.paginate(model, pageable);
        return "user/user-bills";
    }
}
