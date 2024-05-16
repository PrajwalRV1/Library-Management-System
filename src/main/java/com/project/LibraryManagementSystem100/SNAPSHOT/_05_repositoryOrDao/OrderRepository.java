package com.project.LibraryManagementSystem100.SNAPSHOT._05_repositoryOrDao;

import com.project.LibraryManagementSystem100.SNAPSHOT._02_entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
