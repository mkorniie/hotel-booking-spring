package ua.mkorniie.controller.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.mkorniie.model.pojo.Request;

public interface RequestRepository extends PagingAndSortingRepository<Request, Long> {
}
