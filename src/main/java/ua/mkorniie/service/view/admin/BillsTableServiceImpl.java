package ua.mkorniie.service.view.admin;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ua.mkorniie.dao.BillRepository;
import ua.mkorniie.model.pojo.Bill;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static ua.mkorniie.service.util.directions.Pages.ADMIN_BILLS_PAGE;

@Slf4j
@Controller
public class BillsTableServiceImpl implements BillsTableService {
    private final BillRepository billRepository;

    @Autowired
    public BillsTableServiceImpl(BillRepository billRepository) {
        this.billRepository = billRepository;
    }


    private List<Bill> getSubset(@NonNull Iterable<Bill> allBills, @NonNull Pageable pageable) {
        return StreamSupport.stream(allBills.spliterator(), false)
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
    }


    @Override
    public String getBills(@NonNull Pageable pageable, @NonNull Model model) {
        Iterable<Bill> allBills = billRepository.findAll();

        model.addAttribute("total", StreamSupport.stream(allBills.spliterator(), false)
                .filter(Bill::isPaid)
                .mapToDouble(Bill::getSum)
                .sum());

        List<Bill> subset = getSubset(allBills, pageable);

        model.addAttribute("page", new PageImpl<>(subset,
                pageable,
                IterableUtils.size(allBills)));

        return ADMIN_BILLS_PAGE.getCropPath();
    }
}
