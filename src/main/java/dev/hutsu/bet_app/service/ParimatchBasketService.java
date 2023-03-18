//package dev.hutsu.bet_app.service;
//
//
//import dev.hutsu.bet_app.basketball.model.EventBasketball;
//import dev.hutsu.bet_app.basketball.model.LeagueBasketball;
//import dev.hutsu.bet_app.basketball.service.EventBasketService;
//import dev.hutsu.bet_app.basketball.service.LeagueBasketService;
//import dev.hutsu.bet_app.logic.FilterBasket;
//import dev.hutsu.bet_app.model.Country;
//import dev.hutsu.bet_app.parsers.ParseBasket;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class ParimatchBasketService {
//
//    private final CountryService countryService;
//    private final LeagueBasketService leagueBasketService;
//    private final EventBasketService eventBasketService;
//
//    @Autowired
//    public ParimatchBasketService(CountryService countryService, LeagueBasketService leagueBasketService, EventBasketService eventBasketService) {
//        this.countryService = countryService;
//        this.leagueBasketService = leagueBasketService;
//        this.eventBasketService = eventBasketService;
//    }
//
//    public void saveEvents(){
//
//        List<Country> countryList = new FilterBasket().filterCountry();
//
//        for (Country country : countryList) {
//
//            country.setId(countryService.save(country).getId());
//            System.out.println("==========");
//            System.out.println(country.getId());
//            System.out.println(country.getTitle());
//            for (LeagueBasketball leagueBasketball : country.getLeagueBasketballs()) {
//                leagueBasketball.setCountry(country);
//
//                LeagueBasketball tmp = leagueBasketService.save(leagueBasketball);
//
//                leagueBasketball.setId(tmp.getId());
//                System.out.println("=========");
//                System.out.println(leagueBasketball.getTitle());
//                System.out.println(leagueBasketball.getId());
//
//                for (EventBasketball basketball : leagueBasketball.getEventBasketballs()) {
//                    basketball.setLeagueBasketball(leagueBasketball);
//                    basketball = eventBasketService.save(basketball);
//                    System.out.println("========");
//                    System.out.println(basketball.getId());
//                    System.out.println(basketball.getFirstTeam() + " ======== " + basketball.getSecondTeam());
//                }
//            }
//        }
//    }
//
//    public static Country testList(){
//
//        List<EventBasketball> eventBasketballs = new ArrayList<>();
//        EventBasketball basketball = EventBasketball.builder()
//                .firstTeam("TestCommandFirst")
//                .dateEvent(LocalDateTime.now())
//                .secondTeam("TestCommandSecond")
//                .url("https://parimatch.net/ru/events/washington-wizards-toronto-raptors-976785555063")
//                .build();
//        eventBasketballs.add(basketball);
//
//        List<LeagueBasketball> leagueBasketballs = new ArrayList<>();
//        LeagueBasketball leagueBasketball1 = LeagueBasketball.builder()
//                .title("TestTitleLeague")
//                .url("https://parimatch.net/ru/basketball/regular-season-dc909d979cb0401caa6c0841f0d9351a/prematch/0-12")
//                .eventBasketballs(eventBasketballs)
//                .build();
//        leagueBasketballs.add(leagueBasketball1);
//
//
//        return Country.builder()
//                .title("TestCountry1")
//                .leagueBasketballs(leagueBasketballs)
//                .build();
//
//
//    }
//
//    public void testApp() {
//        new ParseBasket().parseEvent();
//    }
//}
