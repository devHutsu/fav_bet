package dev.hutsu.bet_app.volleyball.repo;

import dev.hutsu.bet_app.volleyball.model.EventVolleyball;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventVollRepo extends JpaRepository<EventVolleyball, Integer> {

    EventVolleyball findByUrl(String url);

    Optional<EventVolleyball> findByUrlContaining(String id);

    Optional<EventVolleyball> findByDataId(Integer id);

    @Query("SELECT e FROM EventVolleyball e WHERE e.dataEvent < :dateTime")
    List<EventVolleyball> getByDateLess(@Param("dateTime") LocalDateTime dateTime);

}
