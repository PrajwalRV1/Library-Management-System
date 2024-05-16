package com.project.LibraryManagementSystem100.SNAPSHOT._04_manager;

import com.project.LibraryManagementSystem100.SNAPSHOT._02_entity.Fine;
import com.project.LibraryManagementSystem100.SNAPSHOT._02_entity.User;
import com.project.LibraryManagementSystem100.SNAPSHOT._05_repositoryOrDao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManagerImpl implements UserManager{

    @Autowired
    UserRepository userRepository;

    @Override
    public void createUser(String username) {
        Fine fine = Fine.builder()
                .amount(0)
                .build();
        User user = User.builder()
                .username(username)
                .fine(fine)
                .build();
        userRepository.save(user);
    }
}
