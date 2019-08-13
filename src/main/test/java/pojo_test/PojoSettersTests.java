package java.pojo_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.mkorniie.model.pojo.Bill;
import ua.mkorniie.model.pojo.Request;
import ua.mkorniie.model.pojo.Room;
import ua.mkorniie.model.pojo.User;

public class PojoSettersTests {
    @Test
    void userSetterTest() {
        User user = new User();

        Assertions.assertThrows(NullPointerException.class, () ->
                user.setRole(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                user.setName(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                user.setLanguage(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                user.setEmail(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                user.setPasswordEncoded(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                user.setId(null));

    }

    @Test
    void roomSetterTest() {
        Room room = new Room();

        Assertions.assertThrows(NullPointerException.class, () ->
                room.setPicURL(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                room.setId(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                room.setRoomClass(null));
    }

    @Test
    void roomPriceSetterTest() {
        Room room = new Room();

        room.setPrice(100);
        assert (room.getPrice() == 100);

        room.setPrice(100.01);
        assert (room.getPrice() == 100.01);

        room.setPrice(100.015);
        assert (room.getPrice() == 100.02);

        room.setPrice(100.013);
        assert (room.getPrice() == 100.01);
    }

    @Test
    void requestSetterTest() {
        Request request = new Request();

        Assertions.assertThrows(NullPointerException.class, () ->
                request.setStatus(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                request.setRoomClass(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                request.setUser(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                request.setEndDate(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                request.setId(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                request.setStartDate(null));
    }

    @Test
    void billSetterTest() {
        Bill bill = new Bill();

        Assertions.assertThrows(NullPointerException.class, () ->
                bill.setRoom(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                bill.setRequest(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                bill.setId(null));
    }

}
