package ua.mkorniie.controller.service.view.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import javax.validation.constraints.NotNull;

public interface BillsTableService {
    String getBills(@NotNull Pageable pageable, @NotNull Model model);
}
