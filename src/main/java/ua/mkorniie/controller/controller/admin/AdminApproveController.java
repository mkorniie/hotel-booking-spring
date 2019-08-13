package ua.mkorniie.controller.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.mkorniie.controller.service.view.admin.RequestService;
import ua.mkorniie.model.pojo.Request;
import ua.mkorniie.model.util.directions.Pathes;

@Slf4j
@RequestMapping
@Controller
public class AdminApproveController {
    private final RequestService service;

    @Autowired
    public AdminApproveController(RequestService service) {
        this.service = service;
    }

    @GetMapping("/admin/")
    public String getMain(Model model,
                          @RequestParam(name ="method", required = false) String method,
                          @RequestParam(name = "id", required = false) String id,
                          @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        if(method != null && id != null) {
            if (method.equals("approve")) {
                Request selected = service.validSelected(id);
                if (selected != null){
                    return service.showApprove(model, selected);
                }


            } else if (method.equals("cancel")) {
                service.cancel(id);
            }

        }

        return service.getRequests(model, pageable);
    }


    //TODO: add not null annotation
    @PostMapping("/admin/approve")
    public String approveRequest( Model model,
                                 @RequestParam("id") Long requestId,
                                 @RequestParam("room-select") Long roomId) {

        if (!service.approve(requestId, roomId)) {
            model.addAttribute("method", "approve");
            model.addAttribute("id", requestId);
        }
        return "redirect:" + Pathes.ADMIN_MAIN.getUrl();
    }

}
