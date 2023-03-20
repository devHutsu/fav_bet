package dev.hutsu.bet_app.scheduling;
import dev.hutsu.bet_app.selenium.util.BrowserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTest {

    @Autowired
    private  BrowserUtil browserUtil;

    @Scheduled(cron = "0 0 0/3 * * ?")
    public void updateBasket(){
        Runnable runnable = browserUtil::sourceFavBetVolleyball;
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
