package com.project.LibraryManagementSystem100.SNAPSHOT._05_repositoryOrDao;

import com.project.LibraryManagementSystem100.SNAPSHOT._02_entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
}
