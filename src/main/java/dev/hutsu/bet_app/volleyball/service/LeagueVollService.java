package dev.hutsu.bet_app.volleyball.service;


import dev.hutsu.bet_app.model.Country;
import dev.hutsu.bet_app.volleyball.model.LeagueVolleyball;
import dev.hutsu.bet_app.volleyball.repo.LeagueVollRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LeagueVollService {

    private LeagueVollRepo leagueVollRepo;

    @Autowired
    public LeagueVollService(LeagueVollRepo leagueVollRepo) {
        this.leagueVollRepo = leagueVollRepo;
    }

    public LeagueVolleyball saveOrGet(LeagueVolleyball leagueVolleyball){
        LeagueVolleyball volleyball = leagueVollRepo.findByTitleAndCountry_Id(leagueVolleyball.getTitle(),
                leagueVolleyball.getCountry().getId());

        if (volleyball == null){
            volleyball = leagueVollRepo.save(leagueVolleyball);
        }
        return volleyball;
    }

}
