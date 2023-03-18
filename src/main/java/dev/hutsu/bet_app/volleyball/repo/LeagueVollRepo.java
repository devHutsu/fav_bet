package dev.hutsu.bet_app.volleyball.repo;

import dev.hutsu.bet_app.volleyball.model.LeagueVolleyball;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueVollRepo extends JpaRepository<LeagueVolleyball, Integer> {
    LeagueVolleyball findByTitleAndCountry_Id(String title, Integer id);
}
