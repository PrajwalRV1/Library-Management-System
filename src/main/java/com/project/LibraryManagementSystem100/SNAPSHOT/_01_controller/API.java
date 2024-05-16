package com.project.LibraryManagementSystem100.SNAPSHOT._01_controller;

import com.project.LibraryManagementSystem100.SNAPSHOT._04_manager.BookManager;
import com.project.LibraryManagementSystem100.SNAPSHOT._04_manager.FineManager;
import com.project.LibraryManagementSystem100.SNAPSHOT._04_manager.OrderManager;
import com.project.LibraryManagementSystem100.SNAPSHOT._04_manager.UserManager;
import com.project.LibraryManagementSystem100.SNAPSHOT.modelDto.BookDto;
import com.project.LibraryManagementSystem100.SNAPSHOT.modelDto.FineDto;
import com.project.LibraryManagementSystem100.SNAPSHOT.modelDto.OrderDto;
import com.project.LibraryManagementSystem100.SNAPSHOT.modelDto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class API {

    @Autowired
    BookManager bookManager;

    @Autowired
    UserManager userManager;

    @Autowired
    OrderManager orderManager;

    @Autowired
    FineManager fineManager;


    // Create a book
    @PostMapping("/api/book")
    ResponseEntity<Void> addBook(@RequestBody BookDto bookDto) {
        bookManager.addBook(bookDto);
        return ResponseEntity.ok().build();
    }

    // Get a book
    @GetMapping("/api/book")
    ResponseEntity getBooks(@RequestParam("title") @RequestBody String title) {
        try {
            BookDto bookDto = bookManager.getBooks(title);
            return ResponseEntity.ok(bookDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Create a user
    @PostMapping("/api/user")
    ResponseEntity createUser(@RequestBody  UserDto userDto) {
        userManager.createUser(userDto.getUsername());
        return ResponseEntity.ok().build();
    }

    // Create order
    @PostMapping("/api/order")
    ResponseEntity createOrder(@RequestBody OrderDto orderDto){
        try {
            orderManager.borrowBook(orderDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // return book
    @DeleteMapping("/api/order/username/{username}/title/{title}")
    ResponseEntity returnBook(@PathVariable("username") String username,
                              @PathVariable("title") String title) {
        OrderDto orderDto = new OrderDto();
        orderDto.setUsername(username);
        orderDto.setTitle(title);
        try {
            orderManager.returnBook(orderDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get Fine
    @GetMapping("/api/fine/user/{username}")
    ResponseEntity getFine(@PathVariable("username")  String username){
        try {
            Integer amount = fineManager.getForUser(username);
            return ResponseEntity.ok(amount);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Pay Fine
    @PutMapping("/api/fine")
    ResponseEntity payFine(@RequestBody FineDto fineDto){
        try {
            fineManager.payForUser(fineDto.getUsername(),fineDto.getAmount() );
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
