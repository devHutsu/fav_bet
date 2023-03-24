package dev.hutsu.bet_app.scheduling;
import dev.hutsu.bet_app.selenium.util.BrowserUtil;
import dev.hutsu.bet_app.volleyball.service.EventVollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledApp {

    @Autowired
    private  BrowserUtil browserUtil;

    @Autowired
    private EventVollService eventVollService;

    @Scheduled(cron = "0 0 0/3 * * ?")
    public void updateBasket(){
        Runnable runnable = browserUtil::sourceFavBetVolleyball;
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Scheduled(cron = "0 0 21 * * *")
    public void clearEvent(){
        Runnable runnable = eventVollService::deleteDateLess;
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
