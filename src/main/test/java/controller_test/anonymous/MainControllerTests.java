package java.controller_test.anonymous;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.mkorniie.controller.controller.general.MainController;

import static ua.mkorniie.service.util.directions.Pages.INDEX_PAGE;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainController.class)
public class MainControllerTests {
    @Autowired
    private MainController controller;

    @Test
    public void contextLoads() {
        assert(controller != null);
    }

    @Test
    public void index() {
        assert(controller.index().equals(INDEX_PAGE.getCropPath()));
    }
}
