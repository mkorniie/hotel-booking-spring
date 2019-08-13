package ua.mkorniie.controller.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.mkorniie.model.pojo.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByName(String name);
}
