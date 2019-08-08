package ua.mkorniie.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import ua.mkorniie.controller.dao.BillRepository;
import ua.mkorniie.controller.dao.RequestRepository;
import ua.mkorniie.controller.dao.RoomRepository;
import ua.mkorniie.controller.dao.UserRepository;

//TODO: what is Spring bean??
@Getter
public class Repo {
    @Autowired private static BillRepository billRepository;
    @Autowired private static RequestRepository requestRepository;
    @Autowired private static RoomRepository roomRepository;
    @Autowired private static UserRepository userRepository;

    public static BillRepository billRepository() {
        return billRepository;
    }

    public static RequestRepository requestRepository() {
        return requestRepository;
    }

    public static RoomRepository roomRepository() {
        return roomRepository;
    }

    public static UserRepository userRepository() {
        return userRepository;
    }

    private Repo() {}
}
