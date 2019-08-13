package java.pojo_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.mkorniie.model.enums.Language;
import ua.mkorniie.model.enums.Role;
import ua.mkorniie.model.enums.RoomClass;
import ua.mkorniie.model.enums.Status;
import ua.mkorniie.model.pojo.Bill;
import ua.mkorniie.model.pojo.Request;
import ua.mkorniie.model.pojo.Room;
import ua.mkorniie.model.pojo.User;

class PojoConstructorTest {
    @Test
    void user() {

        Assertions.assertThrows(NullPointerException.class,
                () -> new User(null, Role.USER, "passwordEncoded", "email", Language.en));

        Assertions.assertThrows(NullPointerException.class,
                () -> new User("name", null, "passwordEncoded", "email", Language.en));

        Assertions.assertThrows(NullPointerException.class,
                () -> new User("name", Role.USER, null, "email", Language.en));

        Assertions.assertThrows(NullPointerException.class,
                () -> new User("name", Role.USER, "passwordEncoded", null, Language.en));

        Assertions.assertThrows(NullPointerException.class,
                () -> new User("name", Role.USER, "passwordEncoded", "email", null));

    }

    @Test
    void room() {

        Assertions.assertThrows(NullPointerException.class,
                () -> new Room(1, null, "picURL", 100.99));

        Assertions.assertThrows(NullPointerException.class,
                () -> new Room(1, RoomClass.first, null, 100.99));
    }

    @Test
    void request() {

        Assertions.assertThrows(NullPointerException.class,
                () -> new Request(null, 2, RoomClass.first, "today",
                        "tomorrow", Status.waitingForApproval));

        Assertions.assertThrows(NullPointerException.class,
                () -> new Request(Mockito.mock(User.class), 2, null, "today",
                        "tomorrow", Status.waitingForApproval));

        Assertions.assertThrows(NullPointerException.class,
                () -> new Request(Mockito.mock(User.class), 2, RoomClass.first, null,
                        "tomorrow", Status.waitingForApproval));

        Assertions.assertThrows(NullPointerException.class,
                () -> new Request(Mockito.mock(User.class), 2, RoomClass.first, "t",
                        null, Status.waitingForApproval));

        Assertions.assertThrows(NullPointerException.class,
                () -> new Request(Mockito.mock(User.class), 2, RoomClass.first, "t",
                        "a", null));
    }

    @Test
    void bill() {

        Assertions.assertThrows(NullPointerException.class,
                () -> new Bill(100.01, true,  null,
                        Mockito.mock(Room.class)));

        Assertions.assertThrows(NullPointerException.class,
                () -> new Bill(100.01, true,  Mockito.mock(Request.class),
                        null));
    }
}
