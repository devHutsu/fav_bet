//package dev.hutsu.bet_app.logic;
//
//import dev.hutsu.bet_app.basketball.model.EventBasketball;
//import dev.hutsu.bet_app.basketball.model.LeagueBasketball;
//import dev.hutsu.bet_app.model.Country;
//import dev.hutsu.bet_app.parsers.ParseBasket;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class FilterBasket {
//
//    public List<Country> filterCountry() {
//
//        List<Country> countries = new ParseBasket().parseEvent();
//        for (Country country : countries) {
//            for (LeagueBasketball leagueBasketball : country.getLeagueBasketballs()) {
//
//                if (leagueBasketball.getEventBasketballstBasketballs() == null) break;
//                    List<EventBasketball> eventBasketballs = leagueBasketball.getEventBasketballs()
//                            .stream()
//                            .filter(basketball -> (basketball.getCoeffFirstTeam() > 1.83 && basketball.getCoeffFirstTeam() < 2.38)
//                                    || (basketball.getCoeffSecondTeam() > 1.83 && basketball.getCoeffSecondTeam() < 2.38))
//                            .collect(Collectors.toList());
//
//                    leagueBasketball.setEventBasketballs(eventBasketballs);
//
//
//            }
//
//            List<LeagueBasketball> basketballList = country.getLeagueBasketballs()
//                    .stream()
//                    .filter(leagueBasketball -> leagueBasketball.getEventBasketballs() != null )
//                    .filter(leagueBasketball ->  leagueBasketball.getEventBasketballs().size() > 0)
//                    .collect(Collectors.toList());
//            country.setLeagueBasketballs(basketballList);
//
//
//
//        }
//
//        countries = getCountries(countries);
//
//        System.out.println("=====================");
//
//        countries = new ParseBasket().parseCoeffQuarter(countries);
//
//        for (Country country: countries){
//            for (LeagueBasketball leagueBasketball: country.getLeagueBasketballs()){
//                List<EventBasketball> results = leagueBasketball.getEventBasketballs()
//                        .stream()
//                        .filter(basketball -> basketball.getCoeffFirstQuarter_p1() != null
//                                && basketball.getCoeffSecondQuarter_p2() != null)
//                        .collect(Collectors.toList());
//
//                leagueBasketball.setEventBasketballs(results);
//            }
//        }
//
//        countries = getCountries(countries);
//
//
//        return countries;
//    }
//
//
//
//
//    private List<Country> getCountries(List<Country> countries) {
//        countries = countries
//                .stream()
//                .filter(country -> country.getLeagueBasketballs().size() > 0)
//                .collect(Collectors.toList());
//
//        System.out.println(countries.size());
//        countries.stream().forEach(country -> country.getLeagueBasketballs().stream()
//                .forEach(leagueBasketball -> leagueBasketball.getEventBasketballs().stream()
//                        .forEach(basketball -> System.out.println(basketball.getCoeffFirstQuarter_p1() + " ==== " + basketball.getCoeffSecondQuarter_p2()))));
//        return countries;
//    }
//}
//
//
//
