package dev.hutsu.bet_app;

import dev.hutsu.bet_app.selenium.util.BrowserUtil;
import dev.hutsu.bet_app.telegram.TelegramBotBasketPari;
import dev.hutsu.bet_app.volleyball.service.EventVollService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class TestApp implements CommandLineRunner {

    @Autowired
    private TelegramBotBasketPari botBasketPari;
    private BrowserUtil browserUtil;

    @Autowired
    private EventVollService eventVollService;

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

//        browserUtil.sourceCoeffSet("https://www.favbet.ua/uk/live/event/volleyball/39148550/", 3);


//        ---------------Для запуска программы ----------------------------------

        startLiveParse();



//      ------------------------------------------------------------------------



//        eventVollService.deleteDateLess();


//        updateTest();




    }

    @SneakyThrows
    private void startLiveParse(){

        Runnable runnableLive = () -> browserUtil.liveVoll();
        Thread threadLive = new Thread(runnableLive);
        threadLive.start();

        while (true){
            Thread.sleep(50_000);
            if (threadLive.getState() == Thread.State.TERMINATED){
                threadLive = new Thread(runnableLive);
                threadLive.start();
            }
        }


    }

    private void updateTest(){
        Runnable runnable = browserUtil::sourceFavBetVolleyball;
        Thread thread = new Thread(runnable);
        thread.start();
    }





}
