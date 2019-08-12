package ua.mkorniie.controller.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ua.mkorniie.model.pojo.Room;

public interface RoomRepository extends PagingAndSortingRepository<Room, Long> {
}
