package ua.mkorniie.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.mkorniie.model.pojo.Bill;

public interface BillRepository extends PagingAndSortingRepository<Bill, Long> {
    Bill findByRequestId(Long request_id);
    Page<Bill> findAllByRequest_UserId(Long id, Pageable pageable);
}
