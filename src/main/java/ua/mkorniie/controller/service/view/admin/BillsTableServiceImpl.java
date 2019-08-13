package ua.mkorniie.controller.service.view.admin;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ua.mkorniie.controller.dao.BillRepository;
import ua.mkorniie.model.pojo.Bill;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static ua.mkorniie.model.util.directions.Pages.ADMIN_BILLS_PAGE;

@Slf4j
@Controller
public class BillsTableServiceImpl implements BillsTableService {
    private final BillRepository billRepository;

    @Autowired
    public BillsTableServiceImpl(BillRepository billRepository) {
        this.billRepository = billRepository;
    }


    public List<Bill> getSubset(Iterable<Bill> allBills, Pageable pageable) {
        return StreamSupport.stream(allBills.spliterator(), false)
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
    }


    @Override
    public String getBills(@NotNull Pageable pageable, @NotNull Model model) {
        Iterable<Bill> allBills = billRepository.findAll();

        model.addAttribute("total", StreamSupport.stream(allBills.spliterator(), false)
                .filter(b -> b.isPaid())
                .mapToDouble(b -> b.getSum())
                .sum());

        List<Bill> subset = getSubset(allBills, pageable);

        model.addAttribute("page", new PageImpl<>(subset,
                pageable,
                IterableUtils.size(allBills)));

        return ADMIN_BILLS_PAGE.getCropURL();
    }
}
