package ua.mkorniie.controller.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.mkorniie.model.pojo.Bill;
import ua.mkorniie.model.pojo.Request;

public interface BillRepository extends PagingAndSortingRepository<Bill, Long> {
    Bill findByRequestId(Long request_id);
}
