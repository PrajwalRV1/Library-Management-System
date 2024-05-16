package com.project.LibraryManagementSystem100.SNAPSHOT._03_error;

public class BookNotAvailable extends Exception{
    public BookNotAvailable(String message)  {
        super(message);
    }
}
