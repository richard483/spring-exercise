package com.example.userCRUD.repositories;

import com.example.userCRUD.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findUserByNameContains(String name);
    Page<User> findUserByNameContains(String name, Pageable pageable);

    User findUserById(Integer id);

    Page<User> findAll(Pageable pageable);
}
