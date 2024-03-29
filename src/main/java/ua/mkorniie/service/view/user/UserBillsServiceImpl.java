package ua.mkorniie.service.view.user;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ua.mkorniie.dao.BillRepository;
import ua.mkorniie.dao.RequestRepository;
import ua.mkorniie.model.pojo.Bill;
import ua.mkorniie.model.pojo.Request;
import ua.mkorniie.service.security.HotelUserDetails;

import java.util.Optional;

@Slf4j
@Service
public class UserBillsServiceImpl implements UserBillsService {
    private final BillRepository billRepository;
    private final RequestRepository requestRepository;

    @Autowired
    public UserBillsServiceImpl(BillRepository billRepository, RequestRepository requestRepository) {
        this.billRepository = billRepository;
        this.requestRepository = requestRepository;
    }

    private Optional<Bill> findBill(@NonNull String billId) {
        log.info("Attempting to find Bill with String id=" + billId);
        Optional<Bill> billOptional = Optional.empty();
        try {
            billOptional = billRepository.findById(Long.parseLong(billId));
        } catch (Exception e) {
            log.error("Failure: Bill with id=" + billId + " wasn't found \n" +
                    "Thrown: " + e.getMessage());
        }
        return billOptional;
    }

    @Override
    public void pay(@NonNull String billId) {
        log.info("Attempting to pay Bill with id=" + billId);
        Optional<Bill> billOptional = findBill(billId);

        if (billOptional.isPresent()) {
            Bill bill = billOptional.get();
            bill.setPaid(true);
            billRepository.save(bill);
            log.info("Success");
        } else {
            log.error("Error: Bill hasn't been paid");
        }
    }

    @Override
    public void paginate(@NonNull HotelUserDetails principal, @NonNull Model model, @NonNull Pageable pageable) {
        Page<Bill> page = billRepository.findAllByRequest_UserId(principal.getUser().getId(), pageable);
        model.addAttribute("page", page);
    }

    @Override
    public void cancel(@NonNull String billId) {
        log.info("Attempting to delete Bill with id=" + billId + " and corresponding Request");
        Optional<Bill> billOptional = findBill(billId);
            if (billOptional.isPresent()) {
                Bill bill = billOptional.get();
                Request request = bill.getRequest();
                billRepository.delete(bill);
                requestRepository.delete(request);
                log.info("Success");
            } else {
                log.error("Failure: Bill with id=" + billId + " hasn't been deleted\n");
            }
    }
}
