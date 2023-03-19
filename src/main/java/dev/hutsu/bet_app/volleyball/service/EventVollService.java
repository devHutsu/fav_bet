package dev.hutsu.bet_app.volleyball.service;


import dev.hutsu.bet_app.model.Country;
import dev.hutsu.bet_app.service.CountryService;
import dev.hutsu.bet_app.volleyball.model.EventVolleyball;
import dev.hutsu.bet_app.volleyball.model.LeagueVolleyball;
import dev.hutsu.bet_app.volleyball.repo.EventVollRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class EventVollService {

    private EventVollRepo eventVollRepo;
    private CountryService countryService;
    private LeagueVollService leagueVollService;


    @Autowired
    public EventVollService(EventVollRepo eventVollRepo, CountryService countryService, LeagueVollService leagueVollService) {
        this.eventVollRepo = eventVollRepo;
        this.countryService = countryService;
        this.leagueVollService = leagueVollService;
    }


    public EventVolleyball saveOrGet(EventVolleyball eventVolleyball){
        EventVolleyball volleyball = eventVollRepo.findByUrl(eventVolleyball.getUrl());

        if (volleyball == null){
            Country country = countryService.saveOrGet(eventVolleyball.getLeagueVolleyball().getCountry());
            eventVolleyball.getLeagueVolleyball().setCountry(country);
            LeagueVolleyball leagueVolleyball = leagueVollService.saveOrGet(eventVolleyball.getLeagueVolleyball());
            eventVolleyball.setLeagueVolleyball(leagueVolleyball);
            volleyball = eventVollRepo.save(eventVolleyball);
        }


        return volleyball;
    }

    public EventVolleyball getByDataId(Integer dat_id){

        Optional<EventVolleyball> eventVolleyball = eventVollRepo.findByDataId(dat_id);

        return eventVolleyball.orElse(null);
    }

    public void save(EventVolleyball eventVolleyball){
        eventVollRepo.save(eventVolleyball);
    }



}
