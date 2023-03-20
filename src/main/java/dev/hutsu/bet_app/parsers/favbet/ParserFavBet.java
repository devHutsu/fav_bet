package dev.hutsu.bet_app.parsers.favbet;

import dev.hutsu.bet_app.model.Country;
import dev.hutsu.bet_app.selenium.util.BrowserUtil;
import dev.hutsu.bet_app.volleyball.model.EventVolleyball;
import dev.hutsu.bet_app.volleyball.model.LeagueVolleyball;
import dev.hutsu.bet_app.volleyball.service.EventVollService;
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

    public  List<EventVolleyball> parseEventVoll(String source){
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

    public void parseLiveVoll(String source, EventVollService service){

        Document document = Jsoup.parse(source);

        Elements containerLeague = document.select("div[data-role$='-accordion-trigger']");

        if (containerLeague.size() > 0){

            for (Element elementLeague: containerLeague){
                Element team_container = elementLeague.child(0).child(0).select("div[class*='EventsContainer_sportName']")
                        .first();

                if(team_container == null) break;

                Elements listEvent = elementLeague.select("div[data-role^='event-id-']");


                if (listEvent.size() > 0){
                    for (Element elementEvent: listEvent){
                        var id_role = ParseUtil.getData_id(elementEvent.attr("data-role"));

                        EventVolleyball eventVolleyball = service.getByDataId(id_role);
                        if (eventVolleyball != null && eventVolleyball.getIsVictory() == null) {

                            String urlLive = ParseUtil.urlLiveEvent(id_role);
                            if (eventVolleyball.getUrlLive() == null)
                                eventVolleyball.setUrlLive(urlLive);

                            Element elementContainerEvent = elementEvent.select("div[class*='EventBody_container__CDzH4']")
                                    .first();
                            if (elementContainerEvent == null) break;

                            elementContainerEvent = elementContainerEvent.child(0);


                            elementContainerEvent = elementContainerEvent.child(0).child(1);

                            Elements set_container = elementContainerEvent.child(0).getElementsByTag("span");
                            Elements total_container = elementContainerEvent.child(1).getElementsByTag("span");

                            int set = Integer.parseInt(set_container.get(0).text()) + Integer.parseInt(set_container.get(1).text()) + 1;

                            String result_set;
                            switch (set){
                                case 1:
                                    result_set = total_container.get(0).text() + ":" + total_container.get(1).text();
                                    eventVolleyball.setRes_1_set(result_set);
                                    service.save(eventVolleyball);
                                    break;
                                case 2:
                                    result_set = total_container.get(0).text() + ":" + total_container.get(1).text();

                                    if (eventVolleyball.getRes_1_set() != null){

                                        int scoreFirst = ParseUtil.scoreFirst(eventVolleyball.getRes_1_set());
                                        int scoreSecond = ParseUtil.scoreSecond(eventVolleyball.getRes_1_set());
                                        if (scoreFirst > 20 && scoreSecond > 20){

                                            if (eventVolleyball
                                                    .getCoeff_2_set_45_5_larger() == null
                                                    &&
                                                    eventVolleyball.getCoeff_2_set_45_5_less() == null){

                                                EventVolleyball volleyballTmp = new BrowserUtil()
                                                        .sourceCoeffSet(eventVolleyball.getUrlLive(), 2);
                                                if (volleyballTmp != null) {
                                                    eventVolleyball.setCoeff_2_set_45_5_larger(volleyballTmp.getCoeff_2_set_45_5_larger());
                                                    eventVolleyball.setCoeff_2_set_45_5_less(volleyballTmp.getCoeff_2_set_45_5_less());
                                                }
                                            }

                                            eventVolleyball.setRes_2_set(result_set);
                                        }else {
                                            eventVolleyball.setIsVictory(true);
                                        }

                                    }else {
                                        eventVolleyball.setRes_2_set(result_set);
                                    }

                                    service.save(eventVolleyball);

                                    break;
                                case 3:
                                    if (eventVolleyball.getRes_2_set() != null){


                                        int scoreFirst = ParseUtil.scoreFirst(eventVolleyball.getRes_2_set());
                                        int scoreSecond = ParseUtil.scoreSecond(eventVolleyball.getRes_2_set());
                                        if (scoreFirst > 20 && scoreSecond > 20){

                                            if (eventVolleyball
                                                    .getCoeff_3_set_45_5_larger() == null
                                                    &&
                                                    eventVolleyball.getCoeff_3_set_45_5_less() == null){

                                                EventVolleyball volleyballTmp = new BrowserUtil()
                                                        .sourceCoeffSet(eventVolleyball.getUrlLive(), 3);
                                                if (volleyballTmp != null) {
                                                    eventVolleyball.setCoeff_3_set_45_5_larger(volleyballTmp.getCoeff_3_set_45_5_larger());
                                                    eventVolleyball.setCoeff_3_set_45_5_less(volleyballTmp.getCoeff_3_set_45_5_less());
                                                }
                                            }

                                            result_set = total_container.get(0).text() + ":" + total_container.get(1).text();
                                            eventVolleyball.setRes_3_set(result_set);
                                        }else {
                                            eventVolleyball.setIsVictory(true);
                                        }
                                    }else {
                                        result_set = total_container.get(0).text() + ":" + total_container.get(1).text();
                                        eventVolleyball.setRes_3_set(result_set);
                                    }

                                    service.save(eventVolleyball);

                                    break;
                                case 4:
                                    if (eventVolleyball.getRes_3_set() != null){

                                        int scoreFirst = ParseUtil.scoreFirst(eventVolleyball.getRes_3_set());
                                        int scoreSecond = ParseUtil.scoreSecond(eventVolleyball.getRes_3_set());
                                        if (scoreFirst > 20 && scoreSecond > 20){

                                            if (eventVolleyball
                                                    .getCoeff_4_set_45_5_larger() == null
                                                    &&
                                                    eventVolleyball.getCoeff_4_set_45_5_less() == null){

                                                EventVolleyball volleyballTmp = new BrowserUtil()
                                                        .sourceCoeffSet(eventVolleyball.getUrlLive(), 4);
                                                if (volleyballTmp != null) {
                                                    eventVolleyball.setCoeff_4_set_45_5_larger(volleyballTmp.getCoeff_4_set_45_5_larger());
                                                    eventVolleyball.setCoeff_4_set_45_5_less(volleyballTmp.getCoeff_4_set_45_5_less());
                                                }
                                            }

                                            result_set = total_container.get(0).text() + ":" + total_container.get(1).text();
                                            eventVolleyball.setRes_4_set(result_set);
                                        }else {
                                            eventVolleyball.setIsVictory(true);
                                        }
                                    }else {
                                        result_set = total_container.get(0).text() + ":" + total_container.get(1).text();
                                        eventVolleyball.setRes_4_set(result_set);
                                    }

                                    service.save(eventVolleyball);

                                    break;
                                default:break;
                            }
                            System.out.println(set);
                            System.out.println(total_container.get(0).text() + " -- " + total_container.get(1).text());
                        }
                    }
                }
            }
        }
    }

    public EventVolleyball getEventVollCoeffSets(String source, int set){


        Document document = Jsoup.parse(source);

        Elements containerTotal = document.select("div[data-role$='-accordion-trigger']:contains(Тотал)");

        if (containerTotal.size() < 1 ) return null;

        Element elementTotalContainer;
        if (containerTotal.size() > 1){
            elementTotalContainer = containerTotal.get(1);
        }else {
            elementTotalContainer = containerTotal.get(0);
        }


        EventVolleyball eventVolleyball = new EventVolleyball();


         Element div_container = elementTotalContainer
                 .select(String.format("div[class*='MarketsTypeSection_titleContainer']:contains(%s Сет)", set))
                 .first();
         if (div_container == null) return null;

         div_container = div_container.nextElementSibling();

         if (div_container == null) return null;

         div_container = div_container
                 .select("div[class*='MarketsTypeSection_outcomeWithNameContainer']:contains(45.5)")
                 .first();

         if (div_container == null) return null;

         Elements coeff_totals = div_container.select("div[class*='MarketOutcomeContainer_outcomeContainer']");

         if (coeff_totals.size() < 2) return null;

         float coeff_larger = Float.parseFloat(Objects.requireNonNull(coeff_totals
                 .get(0)
                 .select("span[class*='OutcomeButton_coef']")
                 .first()).text());

         float coeff_less = Float.parseFloat(Objects.requireNonNull(coeff_totals
                 .get(1)
                 .select("span[class*='OutcomeButton_coef']")
                 .first()).text());
         System.out.println(coeff_larger + " -- " + coeff_less);


        switch (set) {
            case 2 -> {
                eventVolleyball.setCoeff_2_set_45_5_larger(coeff_larger);
                eventVolleyball.setCoeff_2_set_45_5_less(coeff_less);
            }
            case 3 -> {
                eventVolleyball.setCoeff_3_set_45_5_larger(coeff_larger);
                eventVolleyball.setCoeff_3_set_45_5_less(coeff_less);
            }
            case 4 -> {
                eventVolleyball.setCoeff_4_set_45_5_larger(coeff_larger);
                eventVolleyball.setCoeff_4_set_45_5_less(coeff_less);
            }
        }


         return eventVolleyball;

    }



}
