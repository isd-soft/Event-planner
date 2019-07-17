package com.inther.eventplaner.repository;

import com.inther.eventplaner.model.UserDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDAO, Integer> {
    UserDAO findByUsername(String username);
    UserDAO findByEmail(String email);
}
