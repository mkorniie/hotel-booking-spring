package ua.mkorniie.controller.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.mkorniie.controller.service.view.admin.BillsTableService;


@Slf4j
@Controller
public class AdminBillsTableController {
    private BillsTableService service;

    @Autowired
    public AdminBillsTableController(BillsTableService service) {
        this.service = service;
    }

    @GetMapping("/admin/bills")
    public String getBills(Model model,
                           @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return service.getBills(pageable, model);
    }
}
