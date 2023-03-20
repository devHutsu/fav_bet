package dev.hutsu.bet_app.selenium.util;

import dev.hutsu.bet_app.parsers.favbet.ParserFavBet;
import dev.hutsu.bet_app.volleyball.model.EventVolleyball;
import dev.hutsu.bet_app.volleyball.service.EventVollService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class BrowserUtil {
    private String original_window = "";
    private WebDriver driver;
    private final static String URL_BROWSER = "http://37.57.8.139:4444/wd/hub";
    public static final String CHROME = "chrome";

    @Autowired
    private  EventVollService eventVollService;


    public BrowserUtil() {

    }

    @SneakyThrows
    public void sourceFavBetVolleyball(){

        WebDriver wd;

        try {
             wd = startBrowserRemote("https://www.favbet.ua/uk/sports/sport/soccer/");
        }catch (SessionNotCreatedException e){
            log.info("Cеть не доступна");

            return;
        }
        ParserFavBet parserFavBet = new ParserFavBet();


        try {
            new WebDriverWait(wd, Duration.ofSeconds(20))
                    .until(webDriver -> webDriver.findElement(By.xpath("//div[@class='SportsBookSearch_container__3V3-c']")));
        }catch (NoSuchSessionException e){
            log.info("Не обнаружен элемент {}", "WebDriver");
            return;
        }
        Thread.sleep(2_000);

        WebElement element_tag_a_voll = wd.findElement(By.xpath("//a[@href='/uk/sports/sport/volleyball/']"));
        element_tag_a_voll.click();
        Thread.sleep(2_000);

        WebElement webElements_country = wd.findElement(By.xpath("//a[@href='/uk/sports/sport/volleyball/']" +
                "/following-sibling::div[1]"));

        List<WebElement> elements_country = webElements_country
                .findElements(By.cssSelector("div[data-role^='category-id-']"));

        List<EventVolleyball> eventVolleyballList = new ArrayList<>();

        if (elements_country != null && elements_country.size()>0) {

            for (WebElement element : elements_country) {
                element.click();
                Thread.sleep(2_000);
                eventVolleyballList.addAll(parserFavBet.parseEventVoll(wd.getPageSource()));

                log.info(element.getText());

            }

            for (EventVolleyball volleyball : eventVolleyballList) {

                wd.get(volleyball.getUrl());

                Thread.sleep(5_000);

//            new WebDriverWait(wd, Duration.ofSeconds(20))


                volleyball = parserFavBet.parseCoeffTotal(volleyball, wd.getPageSource());

                if (volleyball.getCoeff_1_set_45_5_larger() != null)
                    eventVollService.saveOrGet(volleyball);

//                log.info(" Больше ---- Меньше : " + volleyball.getCoeff_1_set_45_5_larger() + " ---- " + volleyball.getCoeff_1_set_45_5_less());

            }
        }

        closeDriver();
        LocalDateTime timeUpdate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String dateUpdateFormatter = timeUpdate.format(formatter);
        log.info(String.format("Обновленны события волейбола %s", dateUpdateFormatter));



    }

    @SneakyThrows
    private WebDriver startBrowserRemote(String url){
        if (driver == null){
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(CHROME);
            driver = new RemoteWebDriver(new URL(URL_BROWSER), capabilities);
            driver.manage().window().maximize();
            original_window = driver.getWindowHandle();
            driver.get(url);
        }

        return driver;

    }

    public void closeDriver(){
        if (driver != null){
            driver.quit();
            driver = null;
        }
    }

    @SneakyThrows
    public EventVolleyball sourceCoeffSet(String url, int set){


        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(CHROME);
        WebDriver webDriver = new RemoteWebDriver(new URL(URL_BROWSER), capabilities);
        webDriver.manage().window().maximize();
        webDriver.get(url);

        ParserFavBet favBet = new ParserFavBet();

        try {
            new WebDriverWait(webDriver, Duration.ofSeconds(20))
                    .until(wd -> wd.findElement(By.cssSelector("div[class='EventMarkets_twoColumns__b-sex']")));
            Thread.sleep(5_000);

        }catch (TimeoutException exception){
            return null;
        }


        EventVolleyball eventVolleyball = favBet.getEventVollCoeffSets(webDriver.getPageSource(), set);

        webDriver.close();

        return eventVolleyball;


    }

    @SneakyThrows
    public void liveVoll(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(CHROME);

        WebDriver webDriver = new RemoteWebDriver(new URL(URL_BROWSER), capabilities);
        webDriver.manage().window().maximize();
        original_window = webDriver.getWindowHandle();
        webDriver.get("https://www.favbet.ua/uk/live/volleyball/");

        ParserFavBet parserFavBet = new ParserFavBet();

        int tmp = 0;

        while (true) {
            Thread.sleep(5_000);

            if (tmp == 15) {
                webDriver.navigate().refresh();
                tmp = 0;
            }

            try {
                new WebDriverWait(webDriver, Duration.ofSeconds(20))
                        .until(wd -> wd.findElement(By.cssSelector("div[class*='EventsContainer_eventsContainer']")));

            }catch (TimeoutException exception){
                tmp++;
                continue;
            }

            Thread.sleep(5_000);


            tmp++;
            parserFavBet.parseLiveVoll(webDriver.getPageSource(), eventVollService);
        }









    }





}
