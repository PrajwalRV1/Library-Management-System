package com.project.LibraryManagementSystem100.SNAPSHOT._04_manager;

import com.project.LibraryManagementSystem100.SNAPSHOT._02_entity.Fine;
import com.project.LibraryManagementSystem100.SNAPSHOT._02_entity.User;
import com.project.LibraryManagementSystem100.SNAPSHOT._05_repositoryOrDao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FineManagerImpl implements FineManager{

    @Autowired
    private UserRepository userRepository;

    @Override
    public Integer getForUser(String username) throws Exception{
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new Exception("No such user exists")
        );

        return user.getFine().getAmount();
    }

    @Override
    public void payForUser(String username, Integer amount) throws Exception{
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new Exception("No such user exists")
        );

        Fine fine = user.getFine();
        Integer presentAmount = fine.getAmount();
        fine.setAmount(Math.max(0, presentAmount-amount));
        userRepository.save(user);
    }
}
