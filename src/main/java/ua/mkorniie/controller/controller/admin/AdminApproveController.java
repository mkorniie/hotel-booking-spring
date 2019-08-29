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
import org.springframework.web.bind.annotation.RequestParam;
import ua.mkorniie.model.pojo.Request;
import ua.mkorniie.service.util.directions.Pathes;
import ua.mkorniie.service.view.admin.RequestService;

@Slf4j
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
                    return service.showApprove(pageable, model, selected);
                }
            } else if (method.equals("cancel")) {
                service.cancel(id);
            }

        }

        return service.getRequests(model, pageable);
    }


    @PostMapping("/admin/approve")
    public String approveRequest( Model model,
                                  @RequestParam("id") Long requestId,
                                  @RequestParam("room-select") Long roomId,
                                  @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {

        if (!service.approve(requestId, roomId)) {
            model.addAttribute("method", "approve");
            model.addAttribute("id", requestId);
        }
        return "redirect:" + Pathes.ADMIN_MAIN.getUrl();
    }

    @GetMapping("/admin/approve")
    public String getApproveRequest(Model model,
                                    @RequestParam(name = "id", required = false) String id,
                                    @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Request selected = service.validSelected(id);
        if (selected != null){
            return service.showApprove(pageable, model, selected);
        }
        return "redirect:" + Pathes.ADMIN_MAIN.getUrl();
    }

}
