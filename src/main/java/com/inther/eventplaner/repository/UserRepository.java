package com.inther.eventplaner.repository;

import com.inther.eventplaner.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {

}
