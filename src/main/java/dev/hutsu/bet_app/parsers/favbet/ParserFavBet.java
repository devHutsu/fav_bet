package dev.hutsu.bet_app.parsers.favbet;

import dev.hutsu.bet_app.model.Country;
import dev.hutsu.bet_app.volleyball.model.EventVolleyball;
import dev.hutsu.bet_app.volleyball.model.LeagueVolleyball;
import dev.hutsu.bet_app.volleyball.util.ParseUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParserFavBet {

    public static List<EventVolleyball> parseEventVoll(String source){
        Document document = Jsoup.parse(source);

        Elements listLeague = document.select("div[data-role='-accordion-trigger']");

        System.out.println("Колличество Лиг --- " + listLeague.size());

        List<EventVolleyball> eventVolleyballList = new ArrayList<>();

        for (Element elementLeague: listLeague){

            Element team_container = elementLeague.child(0).child(0).select("div[class*='EventsContainer_sportName']")
                    .first();
//            assert team_container != null;
            if(team_container == null) break;

            String titleCountry = ParseUtil.titleCountry(team_container.text());
            String titleLeague = ParseUtil.titleLeague(team_container.text());
            System.out.println(titleCountry
                    + "   ----   " + titleLeague);

            Country country = Country.builder()
                    .title(titleCountry)
                    .build();
            LeagueVolleyball league = LeagueVolleyball.builder()
                    .title(titleLeague)
                    .country(country)
                    .build();

            Elements listEvent = elementLeague.select("div[data-role^='event-id-']");

            if (listEvent.size() > 0){
                for (Element eventContainer : listEvent){

                    String data_id = eventContainer.attr("data-role");

                    String urlEvent = ParseUtil.urlEvent(data_id);
                    System.out.println(urlEvent);

                    Elements listTeam = eventContainer
                            .select("div[class$=EventParticipants_participantMain__pDT2a]");
                    if (listTeam.size() < 2) break;

                    String titleFirstTeam = listTeam.get(0).text();
                    String titleSecondTeam = listTeam.get(1).text();

                    String tmpDate = Objects
                            .requireNonNull(eventContainer.select("div[class*='EventDate_eventDate']")
                                    .first()).text();
                    String tmpTime = Objects
                            .requireNonNull(eventContainer.select("div[class*='EventTime_time']")
                                    .first()).text();


                    LocalDateTime dateEvent = ParseUtil.dateEvent(tmpDate, tmpTime);

                    Elements coeffsContainer = eventContainer.select("div[class*='EventSubRowTable_market']");

                    if (coeffsContainer.size() != 3) break;

                    Elements coeffs = coeffsContainer.get(0)
                            .getElementsByTag("span");

                    if (coeffs.size() != 2) break;

                    float coeff_p1 = Float.parseFloat(coeffs.get(0).text());
                    float coeff_p2 = Float.parseFloat(coeffs.get(1).text());

                    EventVolleyball eventVolleyball = EventVolleyball.builder()
                            .firstTeam(titleFirstTeam)
                            .secondTeam(titleSecondTeam)
                            .dataEvent(dateEvent)
                            .coeff_P1(coeff_p1)
                            .coeff_P2(coeff_p2)
                            .dataId(ParseUtil.getData_id(data_id))
                            .url(urlEvent)
                            .leagueVolleyball(league)
                            .build();

                    eventVolleyballList.add(eventVolleyball);

                    System.out.println(coeff_p1 + "--" + coeff_p2);

                    System.out.println(titleFirstTeam + " --- " + titleSecondTeam);

                    System.out.println(dateEvent);

                }
            }

            System.out.println("Колличество событий ------ " + listEvent.size());
        }

        return eventVolleyballList;

    }

    public EventVolleyball parseCoeffTotal(EventVolleyball eventVolleyball, String source){


        Document document = Jsoup.parse(source);

        Element containerTotal = document.select("div[data-role='-accordion-trigger']:contains(Тотал)").first();

        if (containerTotal != null)
            containerTotal = containerTotal.select("div[class*='MarketsTypeSection_titleContainer']:contains(1 Сет)")
                    .first();

        if (containerTotal != null)
            containerTotal = containerTotal.nextElementSibling();

        if (containerTotal != null)
            containerTotal = containerTotal
                    .select("div[class*='MarketsTypeSection_outcomeWithNameContainer']:contains(45.5)")
                    .first();

        if(containerTotal != null){
            Elements coeffs_totals = containerTotal.select("div[class*='MarketOutcomeContainer_outcomeContainer']");

            eventVolleyball.setCoeff_1_set_45_5_larger(Float
                    .parseFloat(Objects.requireNonNull(coeffs_totals
                            .get(0)
                            .select("span[class*='OutcomeButton_coef']")
                            .first()).text()));
            eventVolleyball.setCoeff_1_set_45_5_less(Float
                    .parseFloat(Objects.requireNonNull(coeffs_totals
                            .get(1)
                            .select("span[class*='OutcomeButton_coef']")
                            .first()).text()));
        }

        return eventVolleyball;
    }

    public static List<EventVolleyball> parseLiveVoll(String source){

        Document document = Jsoup.parse(source);

        Elements containerLeague = document.select("div[data-role$='-accordion-trigger']");

        if (containerLeague.size() > 0){

            for (Element elementLeague: containerLeague){
                Element team_container = elementLeague.child(0).child(0).select("div[class*='EventsContainer_sportName']")
                        .first();

                if(team_container == null) break;

                Elements listEvent = elementLeague.select("div[data-role^='event-id-']");
                System.out.println(listEvent.size());

            }



        }




        System.out.println(containerLeague.size());






        return null;
    }



}
