package java.service_test.admin;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.mkorniie.dao.BillRepository;
import ua.mkorniie.service.view.admin.BillsTableService;
import ua.mkorniie.service.view.admin.BillsTableServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes=BillRepository.class)
public class BillsTableServiceTest {

    private BillsTableService service;
    @Autowired private BillRepository billRepository;

    @Before
    public void init() {
        service = new BillsTableServiceImpl(billRepository);
    }

    @Test
    public void context() {
        assert (service != null);
    }
}
