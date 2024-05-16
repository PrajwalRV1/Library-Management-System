package com.project.LibraryManagementSystem100.SNAPSHOT._04_manager;

public interface FineManager {
    Integer getForUser(String username) throws Exception;
    void payForUser(String username, Integer amount) throws Exception;
}
