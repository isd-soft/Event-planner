package com.inther.eventplaner.repository;

import com.inther.eventplaner.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@SuppressWarnings("ALL")
@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    Event findById(int id);
    Event findByTitle(String title);

    @Transactional
    @Modifying
    @Query(value = "insert into event_user (user_id, event_id, answer) values (:userId, :eventId, :answer)",
            nativeQuery = true)
    void insertParticipant(@Param("userId") Integer userId, @Param("eventId") Integer eventId, @Param("answer") String answer);
}
