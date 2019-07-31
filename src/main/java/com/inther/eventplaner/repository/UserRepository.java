package com.inther.eventplaner.repository;

import com.inther.eventplaner.model.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, Integer> {
    UserDAO findByUsername(String username);
    UserDAO findByEmail(String email);

    @Transactional
    @Query(value = "select * from users join event_user on users.id = event_user.user_id where event_id = :eventId and answer = :answer", nativeQuery = true)
    List<UserDAO> getAllParticipantsInfoForEvent(@Param("eventId") Integer eventId, @Param("answer") String answer);
}
