package dev.hutsu.bet_app.telegram;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class TelegramBotBasketPari extends TelegramLongPollingBot {

    public static Long test_id = Long.valueOf(1103396326);



    @Value("${bot.token}")
    private String token;

    @Value("${bot.username}")
    private String username;

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {


    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @SneakyThrows
    public void sendMessage(String msg){
        SendMessage message = new SendMessage();
        message.setChatId(TelegramBotBasketPari.test_id);
        message.setText(msg);

        execute(message);
    }

}
