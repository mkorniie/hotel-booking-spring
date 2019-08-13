package ua.mkorniie.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.mkorniie.model.pojo.Request;

public interface RequestRepository extends PagingAndSortingRepository<Request, Long> {
}
