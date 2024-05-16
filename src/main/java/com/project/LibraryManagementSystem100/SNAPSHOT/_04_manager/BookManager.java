package com.project.LibraryManagementSystem100.SNAPSHOT._04_manager;

import com.project.LibraryManagementSystem100.SNAPSHOT.modelDto.BookDto;

public interface BookManager {

    void addBook(BookDto book);
    BookDto getBooks(String title) throws Exception;
}
