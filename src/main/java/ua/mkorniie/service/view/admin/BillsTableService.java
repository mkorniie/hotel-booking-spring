package ua.mkorniie.service.view.admin;

import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

public interface BillsTableService {
    String getBills(@NonNull Pageable pageable, @NonNull Model model);
}
