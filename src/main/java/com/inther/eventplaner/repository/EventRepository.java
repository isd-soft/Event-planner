package com.inther.eventplaner.repository;

import com.inther.eventplaner.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@SuppressWarnings("ALL")
@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    Event findById(int id);
    Event findByTitle(String title);

    @Transactional
    @Modifying
    @Query(value = "insert into event_user (user_id, event_id, answer) values (:userId, :eventId, :answer)",
            nativeQuery = true)
    void insertParticipationInfo(@Param("userId") Integer userId, @Param("eventId") Integer eventId, @Param("answer") String answer);

    @Transactional
    @Modifying
    @Query(value = "update event_user set answer = :answer where user_id = :userId and event_id = :eventId",
            nativeQuery = true)
    void updateParticipationInfo(@Param("userId") Integer userId, @Param("eventId") Integer eventId, @Param("answer") String answer);

    @Transactional
    @Query(value = "select count(user_id) from event_user where event_id = :eventId and user_id = :userId", nativeQuery = true)
    public int findRecordCount(@Param("eventId") Integer eventId, @Param("userId") Integer userId);

    @Transactional
    @Query(value = "select email from users join event_user on id = event_user.user_id where event_id = :eventId", nativeQuery = true)
    public List<String> getAllParticipantsEmailsOfEvent(@Param("eventId") Integer eventId);

    @Transactional
    @Query(value = "select user_id from events where id = :eventId", nativeQuery = true)
    public int getOrganizerIdOfEvent(@Param("eventId") Integer eventId);


}
