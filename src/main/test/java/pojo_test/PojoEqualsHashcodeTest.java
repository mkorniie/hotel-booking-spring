package java.pojo_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.mkorniie.model.enums.Language;
import ua.mkorniie.model.enums.Role;
import ua.mkorniie.model.exceptions.NotEnoughDataException;
import ua.mkorniie.model.pojo.User;

public class PojoEqualsHashcodeTest {
    @Test
    void testUser() {
        try {
        assert(new User.Builder()
                .withId(1L)
                .withName("name")
                .withRole(Role.USER)
                .withPasswordEncoded("encoder.encode(pass)")
                .withEmail("email")
                .withLanguage(Language.en)
                .build().equals(
                        new User.Builder()
                                .withId(1L)
                                .withName("name")
                                .withRole(Role.USER)
                                .withPasswordEncoded("encoder.encode(pass)")
                                .withEmail("email")
                                .withLanguage(Language.en)
                                .build()
                ));

            assert(!new User.Builder()
                    .withId(1L)
                    .withName("name")
                    .withRole(Role.USER)
                    .withPasswordEncoded("encoder.encode(pass)")
                    .withEmail("email")
                    .withLanguage(Language.en)
                    .build().equals(
                            new User.Builder()
                                    .withId(2L)
                                    .withName("name")
                                    .withRole(Role.USER)
                                    .withPasswordEncoded("encoder.encode(pass)")
                                    .withEmail("email")
                                    .withLanguage(Language.en)
                                    .build()
                    ));

            assert(!new User.Builder()
                    .withId(1L)
                    .withName("name")
                    .withRole(Role.USER)
                    .withPasswordEncoded("encoder.encode(pass)")
                    .withEmail("email")
                    .withLanguage(Language.en)
                    .build().equals(
                            new User.Builder()
                                    .withId(1L)
                                    .withName("Different Name")
                                    .withRole(Role.USER)
                                    .withPasswordEncoded("encoder.encode(pass)")
                                    .withEmail("email")
                                    .withLanguage(Language.en)
                                    .build()
                    ));
        } catch (NotEnoughDataException e) {
            Assertions.fail("NotEnoughDataException not expected");
        }
    }
}
