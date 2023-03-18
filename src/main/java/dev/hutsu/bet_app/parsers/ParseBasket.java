//package dev.hutsu.bet_app.parsers;
//
//import dev.hutsu.bet_app.basketball.model.EventBasketball;
//import dev.hutsu.bet_app.basketball.model.LeagueBasketball;
//import dev.hutsu.bet_app.model.Country;
//import dev.hutsu.bet_app.model.League;
//import dev.hutsu.bet_app.selenium.util.BrowserUtil;
//import dev.hutsu.bet_app.selenium.util.URLParimatch;
//import dev.hutsu.bet_app.util.ParseDateUtil;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class ParseBasket extends Parser {
//
//    public List<Country> parseEvent(){
//        List<Country> countries = parseCountryAndLeague(URLParimatch.URL_BASKETBALL_0_12);
//
//
//        countries.forEach(country -> System.out.println(country.getLeagueListTmp().size()));
//        for (Country country: countries){
//
//            if (country.getLeagueListTmp() == null) break;
//            List<League> leagueBasketballs = country.getLeagueListTmp()
//                    .stream()
//                    .filter(leagueBasketball -> !leagueBasketball.getTitle().contains("Женщины"))
//                    .collect(Collectors.toList());
//            country.setLeagueListTmp(leagueBasketballs);
//        }
//
//        countries = countries
//                .stream()
//                .filter(country -> country.getLeagueListTmp() != null)
//                .filter(country -> country.getLeagueListTmp().size() > 0)
//                .collect(Collectors.toList());
//
//        countries.forEach(country -> System.out.println(country.getLeagueListTmp().size()));
//
//
//
//
//        BrowserUtil browserUtil = new BrowserUtil();
//
//        if (countries.size() > 0) {
//            for (Country country : countries) {
//
//                List<LeagueBasketball> leagueBasketballs = country.getLeagueListTmp()
//                        .stream().map(league -> LeagueBasketball.builder()
//                                .title(league.getTitle())
//                                .url(URLParimatch.URL_HOME + league.getUrl())
//                                .build()).collect(Collectors.toList());
//
//                country.setLeagueBasketballs(leagueBasketballs);
//
//                if (country.getLeagueBasketballs().size() > 0){
//                    for (LeagueBasketball leagueBasketball:country.getLeagueBasketballs()){
//                        System.out.println(leagueBasketball.getTitle());
//                        var res = browserUtil.getEventsSource(leagueBasketball);
//                        List<EventBasketball> eventBasketballs = new ArrayList<>();
//
//                        Document document = Jsoup.parse(res);
//                        Element tmp = document.select("div[data-onboarding^='tournament-']").first();
//
//                        if (tmp == null) break;
//                        Elements eventsList = tmp.select("div[data-anchor^='event_']");
//
//                        for (Element elementEvent: eventsList){
//
//                            Elements span_title_team = elementEvent
//                                    .getElementsByAttributeValue("data-id", "event-card-competitor-name");
//                            Element span_time = elementEvent
//                                    .getElementsByAttributeValue("data-id", "event-card-time-status").first();
//
//
//                            Elements div_coeff = elementEvent
//                                    .getElementsByAttributeValue("data-id", "event-card-main-market")
//                                    .first()
//                                    .getElementsByAttributeValue("data-id", "outcome");
//
//                            if (div_coeff.size() < 1) break;
//
//                            if (span_title_team.size() != 2) break;
//
//                            float coeff_p1;
//                            float coeff_p2;
//                            try {
//                                coeff_p1 = Float
//                                        .parseFloat(div_coeff.get(0).getElementsByTag("span").text());
//                                coeff_p2 = Float
//                                        .parseFloat(div_coeff.get(2).getElementsByTag("span").text());
//                            }catch (NumberFormatException e){
//                                coeff_p1 = 0;
//                                coeff_p2 = 0;
//                            }
//
//                            var teamFirst = span_title_team.get(0).text();
//                            var teamSecond = span_title_team.get(1).text();
//                            var dataEvent = ParseDateUtil.parseDate(span_time.text());
//                            var urlEvent =URLParimatch.URL_HOME + elementEvent.getElementsByTag("a").first()
//                                    .attr("href");
//
//                            EventBasketball basketball = EventBasketball.builder()
//                                    .firstTeam(teamFirst)
//                                    .secondTeam(teamSecond)
//                                    .url(urlEvent)
//                                    .dateEvent(dataEvent)
//                                    .coeffFirstTeam(coeff_p1)
//                                    .coeffSecondTeam(coeff_p2)
//                                    .build();
//
//                            eventBasketballs.add(basketball);
//                        }
//
//                        if(eventBasketballs.size() > 0)
//                            leagueBasketball.setEventBasketballs(eventBasketballs);
//
//                    }
//                }
//            }
//        }
//
//        browserUtil.closeDriver();
//
//        return countries;
//    }
//
//    public List<Country> parseCoeffQuarter(List<Country> countries){
//
//        BrowserUtil browserUtil = new BrowserUtil();
//
//        for (Country country : countries){
//            for (LeagueBasketball leagueBasketball : country.getLeagueBasketballs()){
//                for (EventBasketball basketball : leagueBasketball.getEventBasketballs()){
//                    var pageSource = browserUtil.getQuarterSource(basketball);
//
//                    if (pageSource != null) {
//                        Document document = Jsoup.parse(pageSource);
//                        Elements elements = document
//                                .select("div[data-id='market-expansion-panel-header-Результат. 1-я четверть']")
//                                .first()
//                                .parent()
//                                .nextElementSibling()
//                                .getElementsByAttributeValue("data-id", "outcome");
//
//                        if (elements.size() > 1) {
//
//                            basketball.setCoeffFirstQuarter_p1(Float
//                                    .parseFloat(elements.get(0).getElementsByTag("span").first().text()));
//                            basketball.setCoeffSecondQuarter_p2(Float
//                                    .parseFloat(elements.get(2).getElementsByTag("span").first().text()));
//                        }
//                    }
//                }
//            }
//        }
//
//
//        browserUtil.closeDriver();
//
//        return countries;
//
//    }
//
//}
