package java.util_test;

import org.junit.jupiter.api.Test;
import ua.mkorniie.service.util.Rounder;
import ua.mkorniie.service.util.StringConverter;

import java.util.Optional;

public class UtilTest {

    @Test
    void testRounder() throws Exception {
        if (Rounder.getDigitsAfterPoint() == 2) {
            assert (Rounder.round(20.0) == 20.0);
            assert (Rounder.round(20.999) == 21.00);
            assert (Rounder.round(300.09) == 300.09);
            assert (Rounder.round(300.099) == 300.1);
        } else {
            throw new Exception("Impossible to execute test");
        }
    }

    @Test
    void testStringConverter() {
        assert (!StringConverter.parseLong("abc").isPresent());

        Optional<Long> res = StringConverter.parseLong("125");
        assert (res.isPresent() && res.get().equals(125L));

        res = StringConverter.parseLong("-125");
        assert (res.isPresent() && res.get().equals(-125L));
    }
}
