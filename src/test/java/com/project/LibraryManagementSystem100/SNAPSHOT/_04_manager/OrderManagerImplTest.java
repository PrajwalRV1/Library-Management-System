package com.project.LibraryManagementSystem100.SNAPSHOT._04_manager;

import com.project.LibraryManagementSystem100.SNAPSHOT._02_entity.Book;
import com.project.LibraryManagementSystem100.SNAPSHOT._02_entity.Fine;
import com.project.LibraryManagementSystem100.SNAPSHOT._02_entity.Order;
import com.project.LibraryManagementSystem100.SNAPSHOT._02_entity.User;
import com.project.LibraryManagementSystem100.SNAPSHOT._03_error.*;
import com.project.LibraryManagementSystem100.SNAPSHOT._05_repositoryOrDao.BookRepository;
import com.project.LibraryManagementSystem100.SNAPSHOT._05_repositoryOrDao.OrderRepository;
import com.project.LibraryManagementSystem100.SNAPSHOT._05_repositoryOrDao.UserRepository;
import com.project.LibraryManagementSystem100.SNAPSHOT.modelDto.BookDto;
import com.project.LibraryManagementSystem100.SNAPSHOT.modelDto.OrderDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class OrderManagerImplTest {

    // Mockito Constructor approach
    //    // Mocking a class
//
//    UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
//    BookRepository bookRepositoryMock = Mockito.mock(BookRepository.class);
//    OrderRepository orderRepositoryMock = Mockito.mock(OrderRepository.class);
//
//    // Creating instance of class under test to inject mocks into it
//    OrderManager orderManager= new OrderManager(userRepositoryMock, bookRepositoryMock, orderRepositoryMock);

    @Mock
    UserRepository userRepository;

    @Mock
    BookRepository bookRepository;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderManagerImpl orderManager;

    @Test
    void borrowBookWithNoUserFound() {
        Mockito.when(userRepository.findByUsername(
                Mockito.anyString()
                )
        ).thenReturn(Optional.empty());

        Assertions.assertThrows(NoUserFound.class,
                ()-> {
                    orderManager.borrowBook(
                        new OrderDto("title", "user")
                    );
                }
                );
    }

    @Test
    void borrowBookWithExistingOrder () {

        Order testOrder = Order.builder().book(new Book()).build();
        User testUser = User.builder().username("user").order(testOrder).build();
        Mockito.when(userRepository.findByUsername(
                Mockito.anyString()
        )
        ).thenReturn(Optional.of(testUser));
        Assertions.assertThrows(OrderAlreadyExists.class,
                    ()-> {
                        orderManager.borrowBook(
                                new OrderDto("title", "user")
                        );
                    }
                );
    }

    @Test
    void borrowBookWithUserAHavingFine() {

        Fine testFine = Fine.builder().amount(10).build();
        User testUser = User.builder().username("user").fine(testFine).build();
        Mockito.when(userRepository.findByUsername(
                        Mockito.anyString()
                )
        ).thenReturn(Optional.of(testUser));
        Assertions.assertThrows(NonZeroFine.class,
                ()-> {
                    orderManager.borrowBook(
                            new OrderDto("title", "user")
                    );
                }
        );
    }

    @Test
    void borrowBookWithNoValidBook () {

        Fine testFine = Fine.builder().amount(0).build();
        User testUser = User.builder().username("user").fine(testFine).build();

        Mockito.when(userRepository.findByUsername(
                        Mockito.anyString()
                )
        ).thenReturn(Optional.of(testUser));

        Mockito.when(bookRepository.findByTitle(
                        Mockito.anyString()
                )
        ).thenReturn(Optional.empty());

        Assertions.assertThrows(BookNotFound.class,
                ()-> {
                    orderManager.borrowBook(
                            new OrderDto("title", "user")
                    );
                }
        );
    }

    @Test
    void borrowBookWithBookNotAvailable () {

        Book testBook = Book.builder().quantity(0).build();
        Fine testFine = Fine.builder().amount(0).build();
        User testUser = User.builder().username("user").fine(testFine).build();

        Mockito.when(userRepository.findByUsername(
                        Mockito.anyString()
                )
        ).thenReturn(Optional.of(testUser));

        Mockito.when(bookRepository.findByTitle(
                        Mockito.anyString()
                )
        ).thenReturn(Optional.of(testBook));

        Assertions.assertThrows(BookNotAvailable.class,
                ()-> {
                    orderManager.borrowBook(
                            new OrderDto("title", "user")
                    );
                }
        );
    }

    @Test
    void borrowBookSuccess () {

        Fine testFine = Fine.builder().amount(0).build();
        User testUser = User.builder().username("user").fine(testFine).build();
        Book testBook = Book.builder().quantity(2).build();

        Mockito.when(userRepository.findByUsername(
                        Mockito.anyString()
                )
        ).thenReturn(Optional.of(testUser));

        Mockito.when(bookRepository.findByTitle(
                        Mockito.anyString()
                )
        ).thenReturn(Optional.of(testBook));

        try {
            orderManager.borrowBook(new OrderDto("title1", "user"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        testBook.setQuantity(1);

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(userRepository, Mockito.times(1)).save(testUser);
        Mockito.verify(bookRepository, Mockito.times(1)).save(Mockito.any(Book.class));
        Mockito.verify(bookRepository, Mockito.times(1)).save(testBook);
    }

    @Test
    void returnBookWithNoUserFound() {

        Mockito.when(userRepository.findByUsername(
                Mockito.anyString()
        )).thenReturn(Optional.empty());

        Assertions.assertThrows(NoUserFound.class,
                ()-> {
                    orderManager.returnBook(new OrderDto("title1", "user"));
                }
                );

    }

    @Test
    void returnBookWithNoOrderExists() {

        User testUser = User.builder().order(null).build();

        Mockito.when(userRepository.findByUsername(
                Mockito.anyString()
        )).thenReturn(Optional.of(testUser));

        Assertions.assertThrows(NoOrderExists.class,
                ()-> {
                    orderManager.returnBook(new OrderDto("title1", "user"));
                }
        );

    }

    @Test
    void returnBookWithTitleNotMatching() {
        Book book = Book.builder().title("title1").build();
        Order order = Order.builder().book(book).build();

        Assertions.assertThrows(Exception.class,
                ()-> {
                    orderManager.returnBook(new OrderDto("title1", "user"));
                }
                );
    }
}