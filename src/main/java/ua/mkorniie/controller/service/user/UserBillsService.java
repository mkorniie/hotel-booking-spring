package ua.mkorniie.controller.service.user;


import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

public interface UserBillsService {
    void pay(String billId);

    void paginate(Model model, Pageable pageable);

    void cancel(String requestId);
}
