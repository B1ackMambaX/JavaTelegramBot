package main;

import bot.TelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.logging.Logger;


/**
 * Основной класс, отвечающий за запуск всех сущностей(ботов)
 */
public class  App
{
    public static void main( String[] args )
    {
        Logger logger = Logger.getLogger(App.class.getName());
        try {
            logger.info("Start App");
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            logger.severe(e.toString());
            e.printStackTrace();
        }
    }
}
