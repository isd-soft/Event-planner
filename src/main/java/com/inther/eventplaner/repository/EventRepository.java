package com.inther.eventplaner.repository;

import com.inther.eventplaner.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    Event findById(int id);
    Event findByTitle(String title);
}
