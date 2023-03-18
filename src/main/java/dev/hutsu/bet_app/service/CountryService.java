package dev.hutsu.bet_app.service;


import dev.hutsu.bet_app.repo.CountryRepo;
import dev.hutsu.bet_app.model.Country;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
@Transactional
public class CountryService {

    private CountryRepo repo;

    @Autowired
    public CountryService(CountryRepo repo) {
        this.repo = repo;
    }


    public Country saveOrGet(Country country){

        var result = repo.findByTitle(country.getTitle());

        if (result == null){
            result = repo.save(country);
        }

        return result;
    }
}
