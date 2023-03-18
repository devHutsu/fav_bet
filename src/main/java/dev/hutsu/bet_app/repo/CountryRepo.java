package dev.hutsu.bet_app.repo;

import dev.hutsu.bet_app.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepo extends JpaRepository<Country, Integer> {
    Country findByTitle(String title);
}
