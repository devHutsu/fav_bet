package dev.hutsu.bet_app;

import dev.hutsu.bet_app.parsers.favbet.ParserFavBet;
import dev.hutsu.bet_app.selenium.util.BrowserUtil;
import dev.hutsu.bet_app.telegram.TelegramBotBasketPari;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@SpringBootApplication
@EnableScheduling
public class TestApp implements CommandLineRunner {

    @Autowired
    private TelegramBotBasketPari botBasketPari;
    private BrowserUtil browserUtil;

    @Autowired
    public TestApp(BrowserUtil browserUtil) {
        this.browserUtil = browserUtil;
    }



    public static void main(String[] args) {
        SpringApplication.run(TestApp.class);
    }

    @Override
    public void run(String... args) throws Exception {
        test();
    }

    private void test() throws InterruptedException {
        browserUtil.liveTest();

//        Thread.sleep(3_000);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                browserUtil.sourceFavBetVolleyball();
//            }
//        }).start();
    }


}
