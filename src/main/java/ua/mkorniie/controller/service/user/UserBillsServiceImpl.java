package ua.mkorniie.controller.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ua.mkorniie.controller.dao.BillRepository;
import ua.mkorniie.controller.dao.RequestRepository;
import ua.mkorniie.model.pojo.Bill;
import ua.mkorniie.model.pojo.Request;

@Slf4j
@Component
public class UserBillsServiceImpl implements UserBillsService {
    private final BillRepository billRepository;
    private final RequestRepository requestRepository;

    @Autowired
    public UserBillsServiceImpl(BillRepository billRepository, RequestRepository requestRepository) {
        this.billRepository = billRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public void pay(String billId) {
        log.info("Attempting to pay Bill with id=" + billId);
        try {
            Bill bill = billRepository.findById(Long.parseLong(billId)).get();
            bill.setPaid(true);
            billRepository.save(bill);
            log.info("Success");
        } catch (Exception e) {
            log.error("Failure: Bill with id=" + billId + " hasn't been paid");
        }
    }

    @Override
    public void paginate(Model model, Pageable pageable) {
        Page<Bill> page = billRepository.findAll(pageable);
        model.addAttribute("page", page);
    }

    @Override
    public void cancel(String requestId) {
        log.info("Attempting to delete Bill with id=" + requestId + " and corresponding Request");
        try {
            Bill bill = billRepository.findById(Long.parseLong(requestId)).get();
            Request request = bill.getRequest();
            billRepository.delete(bill);
            requestRepository.delete(request);
            log.info("Success");
        } catch (Exception e) {
            log.error("Failure: Bill with id=" + requestId + " hasn't been deleted");
        }
    }
}
