package com.example.mvcproducts.repositories;

import com.example.mvcproducts.domain.Order;
import com.example.mvcproducts.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUser(User user);
}