package com.project.LibraryManagementSystem100.SNAPSHOT._04_manager;

import com.project.LibraryManagementSystem100.SNAPSHOT._02_entity.Book;
import com.project.LibraryManagementSystem100.SNAPSHOT._05_repositoryOrDao.BookRepository;
import com.project.LibraryManagementSystem100.SNAPSHOT.modelDto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookManagerImpl implements BookManager {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void addBook(BookDto bookRequest) {
        Book book = Book.builder()
                .title(bookRequest.getTitle())
                .author(bookRequest.getAuthor())
                .quantity(bookRequest.getQuantity())
                .build();
        bookRepository.save(book);
    }

    @Override
    public BookDto getBooks(String title) throws Exception {
        Book book = bookRepository.findByTitle(title).orElseThrow(
                ()-> new Exception("No such title is present")
        );

        return BookDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .quantity(book.getQuantity())
                .build();
    }
}
