package ua.mkorniie.controller.dao;

import ua.mkorniie.model.pojo.Bill;

public class BillDaoProxy {
    private final BillRepository billDAO;

    public BillDaoProxy(BillRepository billDAO) {
        this.billDAO = billDAO;
    }

//    Bill findByRequest(Long request_id) {
//        List<Bills> bills = billDAO.findAll();
//
//    }
}
