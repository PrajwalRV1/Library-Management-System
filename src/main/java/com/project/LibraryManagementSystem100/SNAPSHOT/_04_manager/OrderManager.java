package com.project.LibraryManagementSystem100.SNAPSHOT._04_manager;

import com.project.LibraryManagementSystem100.SNAPSHOT.modelDto.OrderDto;

public interface OrderManager {
    void borrowBook(OrderDto order) throws Exception;
    void returnBook(OrderDto order) throws Exception;
}
