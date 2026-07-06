package ru.foxscribe.simplechat.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.foxscribe.simplechat.model.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.room.id = :roomId AND m.time >= :since")
    List<Message> retrieve(@Param("roomId") Long roomId, @Param("since") Long time);

    @Query("SELECT m FROM Message m WHERE m.room.id = :roomId ORDER BY m.id DESC")
    List<Message> retrieveMessagesPaginated(@Param("roomId") Long roomId, Pageable pageable);
}
