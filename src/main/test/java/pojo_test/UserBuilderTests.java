package java.pojo_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.mkorniie.model.enums.Language;
import ua.mkorniie.model.enums.Role;
import ua.mkorniie.model.exceptions.NotEnoughDataException;
import ua.mkorniie.model.pojo.User;

class UserBuilderTests {

    @Test
    void userBuilderNullTest() {
        Assertions.assertThrows(NullPointerException.class, () ->
                new User.Builder()
                .withId(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                new User.Builder()
                        .withName(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                new User.Builder()
                        .withRole(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                new User.Builder()
                        .withPasswordEncoded(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                new User.Builder()
                        .withEmail(null));

        Assertions.assertThrows(NullPointerException.class, () ->
                new User.Builder()
                        .withLanguage(null));
    }

    @Test
    void userBuilderValidArgumentsTest() {
        try {
            if ((new User.Builder()
                    .withName("name")
                    .withRole(Role.USER)
                    .withPasswordEncoded("encoder.encode(pass)")
                    .withEmail("email")
                    .withLanguage(Language.en)
                    .build() == null)) throw new AssertionError();

            if ((new User.Builder()
                    .withId(1L)
                    .withName("name")
                    .withRole(Role.USER)
                    .withPasswordEncoded("encoder.encode(pass)")
                    .withEmail("email")
                    .withLanguage(Language.en)
                    .build() == null)) throw new AssertionError();
        } catch (NotEnoughDataException e) {
            Assertions.fail("Exception 'NotEnoughDataException' not expected");
        }

    }

    @Test
    void userBuilderInvalidArgumentsTest() {
        Assertions.assertThrows(NotEnoughDataException.class,
                () -> new User.Builder()
                        .withRole(Role.USER)
                        .withPasswordEncoded("encoder.encode(pass)")
                        .withEmail("email")
                        .withLanguage(Language.en)
                        .build());

        Assertions.assertThrows(NotEnoughDataException.class,
                () -> new User.Builder()
                        .withName("name")
                        .withPasswordEncoded("encoder.encode(pass)")
                        .withEmail("email")
                        .withLanguage(Language.en)
                        .build());

        Assertions.assertThrows(NotEnoughDataException.class,
                () -> new User.Builder()
                        .withName("name")
                        .withRole(Role.USER)
                        .withEmail("email")
                        .withLanguage(Language.en)
                        .build());

        Assertions.assertThrows(NotEnoughDataException.class,
                () -> new User.Builder()
                        .withName("name")
                        .withRole(Role.USER)
                        .withPasswordEncoded("encoder.encode(pass)")
                        .withLanguage(Language.en)
                        .build());

        Assertions.assertThrows(NotEnoughDataException.class,
                () -> new User.Builder()
                        .withName("name")
                        .withRole(Role.USER)
                        .withPasswordEncoded("encoder.encode(pass)")
                        .withEmail("email")
                        .build());

        Assertions.assertThrows(NotEnoughDataException.class,
                () -> new User.Builder()
                        .withId(1L)
                        .withRole(Role.USER)
                        .withPasswordEncoded("encoder.encode(pass)")
                        .withEmail("email")
                        .withLanguage(Language.en)
                        .build());

        Assertions.assertThrows(NotEnoughDataException.class,
                () -> new User.Builder()
                        .withId(1L)
                        .withName("name")
                        .withPasswordEncoded("encoder.encode(pass)")
                        .withEmail("email")
                        .withLanguage(Language.en)
                        .build());

        Assertions.assertThrows(NotEnoughDataException.class,
                () -> new User.Builder()
                        .withId(1L)
                        .withName("name")
                        .withRole(Role.USER)
                        .withEmail("email")
                        .withLanguage(Language.en)
                        .build());

        Assertions.assertThrows(NotEnoughDataException.class,
                () -> new User.Builder()
                        .withId(1L)
                        .withName("name")
                        .withRole(Role.USER)
                        .withPasswordEncoded("encoder.encode(pass)")
                        .withLanguage(Language.en)
                        .build());

        Assertions.assertThrows(NotEnoughDataException.class,
                () -> new User.Builder()
                        .withId(1L)
                        .withName("name")
                        .withRole(Role.USER)
                        .withPasswordEncoded("encoder.encode(pass)")
                        .withEmail("email")
                        .build());
    }
}
