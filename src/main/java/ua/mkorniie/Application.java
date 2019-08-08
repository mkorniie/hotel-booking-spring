package ua.mkorniie;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ua.mkorniie.controller.dao.BillRepository;
import ua.mkorniie.controller.dao.RequestRepository;
import ua.mkorniie.controller.dao.RoomRepository;
import ua.mkorniie.controller.dao.UserRepository;
import ua.mkorniie.model.enums.Language;
import ua.mkorniie.model.enums.Role;
import ua.mkorniie.model.enums.RoomClass;
import ua.mkorniie.model.pojo.Bill;
import ua.mkorniie.model.pojo.Request;
import ua.mkorniie.model.pojo.Room;
import ua.mkorniie.model.pojo.User;
import ua.mkorniie.model.util.PasswordEncoder;

import java.sql.Date;


//TODO: fix front in chrome (at least general welcome page)

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    public CommandLineRunner demo(UserRepository userRepository, BillRepository billRepository, RequestRepository requestRepository, RoomRepository roomRepository) {
//        return (args) -> {
////            log.info("Customers found with findAll():");
////            log.info("-------------------------------");
//////            // save a couple of customers
////            userRepository.save(new User("Maria", Role.ADMIN, PasswordEncoder.getSHA("123"), "123@123.com", Language.en));
////            userRepository.save(new User("Anastasia", Role.USER, PasswordEncoder.getSHA("123"), "123@123.com", Language.en));
////
////            for (User user : userRepository.findAll()) {
////                log.info(user.toString());
////            }
////            log.info("");
////
////            log.info("Rooms found with findAll():");
////            log.info("-------------------------------");
//////            // save a couple of customers
////            roomRepository.save(new Room(1, RoomClass.lux, "img/rooms/lux", 600));
////            roomRepository.save(new Room(2, RoomClass.first, "img/rooms/first", 300));
////
////            for (Room room : roomRepository.findAll()) {
////                log.info(room.toString());
////            }
////            log.info("");
//
//            log.info("Requests found with findAll():");
//            log.info("-------------------------------");
////            // save a couple of customers
//            requestRepository.save(new Request(sessionFactory.getCurrentSession().load(User.class, 2), 1, RoomClass.lux, Date.valueOf("2015-03-29"), Date.valueOf("2015-03-30"), false));
//            requestRepository.save(new Request(sessionFactory.getCurrentSession().load(User.class, 2), 9, RoomClass.second, Date.valueOf("2015-03-29"), Date.valueOf("2015-03-30"), false));
//
//            for (Request request : requestRepository.findAll()) {
//                log.info(request.toString());
//            }
//            log.info("");
//
////            log.info("Bills found with findAll():");
////            log.info("-------------------------------");
//////            // save a couple of customers
//////            billRepository.save(new Bill(1000, false, requestRepository.findAll(), 3));
//////            billRepository.save(new Bill(2, RoomClass.first, "img/rooms/first", 300));
////
////            for (Bill bill : billRepository.findAll()) {
////                log.info(bill.toString());
////            }
////            log.info("");
//        };
//    }
}