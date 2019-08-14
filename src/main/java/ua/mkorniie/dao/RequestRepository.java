package ua.mkorniie.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.mkorniie.model.pojo.Request;
import ua.mkorniie.model.pojo.User;

public interface RequestRepository extends PagingAndSortingRepository<Request, Long> {
    Page<Request> findAllByUser(User user, Pageable pageable);
}
