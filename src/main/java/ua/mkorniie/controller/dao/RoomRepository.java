package ua.mkorniie.controller.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import ua.mkorniie.model.pojo.Room;

public interface RoomRepository extends PagingAndSortingRepository<Room, Long> {
}
