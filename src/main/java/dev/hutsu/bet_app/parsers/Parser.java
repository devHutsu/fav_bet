package dev.hutsu.bet_app.parsers;

import dev.hutsu.bet_app.model.Country;
import dev.hutsu.bet_app.model.League;
import dev.hutsu.bet_app.selenium.util.BrowserUtil;
import dev.hutsu.bet_app.selenium.util.URLParimatch;
import dev.hutsu.bet_app.tmp.IOTmp;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public abstract class Parser {

    public List<Country> parseCountryAndLeague(String url){

//        String source = new BrowserUtil().eventsTwentyFourHours(url);
//        IOTmp.savePathEventHtml(source);
        String source = IOTmp.getHTML();

        Document document = Jsoup.parse(source);

        Elements containersCountry = document.getElementsByAttributeValue("data-id", "countries-list").first()
                .select("div[data-id^='country-id-']");

        List<Country> countries = new ArrayList<>();

        for (Element element:containersCountry){

            var titleCountry = element.getElementsByAttributeValue("data-id", "country-title").first().text();

            if (titleCountry.contains("Кибербаскетбол")) break;

            Element containerLeague = element.nextElementSibling();
            Elements leagues = containerLeague.getElementsByTag("li");
            List<League> leagueList = new ArrayList<>();
            for (Element elementLeague:leagues){
                Element element_tag_a = elementLeague.getElementsByTag("a").first();
                var titleLeague = element_tag_a.text();

                if (titleLeague.contains("Дуэль игроков") || titleLeague.contains("Специальные предложения") ||
                titleLeague.contains("Женщины")) break;

                League league = League.builder()
                        .title(titleLeague)
                        .url(element_tag_a.attr("href"))
                        .build();
                leagueList.add(league);
            }

            if (leagueList.size() > 0){
                Country country = Country.builder()
                        .title(titleCountry)
                        .leagueListTmp(leagueList)
                        .build();

                countries.add(country);
            }

        }

        return countries;

    }

}
