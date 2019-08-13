package controller_test.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.mkorniie.Application;
import ua.mkorniie.controller.controller.admin.AdminApproveController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class AdminApproveControllerTests {
    @Autowired private AdminApproveController controller;

    @Test
    public void contextLoads() throws Exception {
        assert(controller != null);
    }
}
