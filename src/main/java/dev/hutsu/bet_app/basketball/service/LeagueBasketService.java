//package dev.hutsu.bet_app.basketball.service;
//
//import dev.hutsu.bet_app.basketball.model.LeagueBasketball;
//import dev.hutsu.bet_app.basketball.repo.LeagueBasketRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//public class LeagueBasketService {
//
//    private LeagueBasketRepo leagueBasketRepo;
//
//    @Autowired
//    public LeagueBasketService(LeagueBasketRepo leagueBasketRepo) {
//        this.leagueBasketRepo = leagueBasketRepo;
//    }
//
//
//    public LeagueBasketball save(LeagueBasketball leagueBasketball){
//
//        LeagueBasketball result = leagueBasketRepo
//                .findByTitleAndCountry_Id(leagueBasketball.getTitle(), leagueBasketball.getCountry().getId());
//
//        if (result == null){
//            result = leagueBasketRepo.save(leagueBasketball);
//        }
//        return result;
//
//    }
//
//
//}
