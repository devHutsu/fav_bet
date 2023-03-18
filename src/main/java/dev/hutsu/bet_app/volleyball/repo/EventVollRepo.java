package dev.hutsu.bet_app.volleyball.repo;

import dev.hutsu.bet_app.volleyball.model.EventVolleyball;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventVollRepo extends JpaRepository<EventVolleyball, Integer> {

    EventVolleyball findByUrl(String url);

    Optional<EventVolleyball> findByUrlContaining(String id);

    Optional<EventVolleyball> findByDataId(Integer id);



}
