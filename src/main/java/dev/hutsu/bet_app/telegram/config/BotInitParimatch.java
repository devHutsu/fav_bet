package dev.hutsu.bet_app.telegram.config;

import dev.hutsu.bet_app.telegram.TelegramBotBasketPari;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotInitParimatch {

    private final TelegramBotBasketPari botBasketPari;

    @Autowired
    public BotInitParimatch(TelegramBotBasketPari botBasketPari) {
        this.botBasketPari = botBasketPari;
    }

//    @EventListener({ContextRefreshedEvent.class})
//    public void init() throws TelegramApiException {
//        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//
//        telegramBotsApi.registerBot(botBasketPari);
//
//    }
}
