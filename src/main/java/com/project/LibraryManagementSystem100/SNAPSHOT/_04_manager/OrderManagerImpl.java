package com.project.LibraryManagementSystem100.SNAPSHOT._04_manager;

import com.project.LibraryManagementSystem100.SNAPSHOT._02_entity.Book;
import com.project.LibraryManagementSystem100.SNAPSHOT._02_entity.Fine;
import com.project.LibraryManagementSystem100.SNAPSHOT._02_entity.Order;
import com.project.LibraryManagementSystem100.SNAPSHOT._02_entity.User;
import com.project.LibraryManagementSystem100.SNAPSHOT._03_error.*;
import com.project.LibraryManagementSystem100.SNAPSHOT._05_repositoryOrDao.BookRepository;
import com.project.LibraryManagementSystem100.SNAPSHOT._05_repositoryOrDao.OrderRepository;
import com.project.LibraryManagementSystem100.SNAPSHOT._05_repositoryOrDao.UserRepository;
import com.project.LibraryManagementSystem100.SNAPSHOT.modelDto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;



@Service
public class OrderManagerImpl implements OrderManager{

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderRepository orderRepository;


    @Override
    public void borrowBook(OrderDto orderReq) throws Exception {
        String title = orderReq.getTitle();
        String username = orderReq.getUsername();
        User user =  userRepository.findByUsername(username).orElseThrow(
                ()-> new NoUserFound("No such user exists.")
        );
        if (user.getOrder() != null){
            throw new OrderAlreadyExists("User is having a book already.");
        }
        if (user.getFine().getAmount() >0){
            throw new NonZeroFine("User has to clear fine.");
        }

        Book book =  bookRepository.findByTitle(title).orElseThrow(
                ()-> new BookNotFound("No such title")
        );
        if(book.getQuantity()<1){
            throw new BookNotAvailable("Book is not available");
        }
        Integer qty = book.getQuantity();
        book.setQuantity(qty-1);

        Order order =  Order.builder()
                .book(book)
                .user(user)
                .createdAt(Date.valueOf(LocalDate.now().minusDays(10l)))
                .build();
        user.setOrder(order);
        userRepository.save(user);
        bookRepository.save(book);
    }

    @Override
    public void returnBook(OrderDto orderReq) throws Exception {
        String title = orderReq.getTitle();
        String username = orderReq.getUsername();

        User user =  userRepository.findByUsername(username).orElseThrow(
                ()-> new NoUserFound("No such user exists.")
        );
        if (user.getOrder() == null){
            throw new NoOrderExists("user doesn't have any order");
        }
        Book book = user.getOrder().getBook();
        Integer qty = book.getQuantity();
        book.setQuantity(qty+1);

        Order order = user.getOrder();
        if(!order.getBook().getTitle().equals(title)){
            throw new TitleNotMatching("user is returning wrong name");
        }
        LocalDate borrowedDate = order.getCreatedAt().toLocalDate();
        long daysBetween = ChronoUnit.DAYS.between(borrowedDate, LocalDate.now());
        Integer fineAmount = 0;
        if(daysBetween > 7){
            fineAmount = ( (int)daysBetween - 7) * 5;
        }

        Fine fine = user.getFine();
        fine.setAmount(fineAmount);
        user.setFine(fine);
        order.setUser(null);
        order.setBook(null);
        user.setOrder(null);
        userRepository.save(user);
        bookRepository.save(book);
        orderRepository.delete(order);


    }
}
