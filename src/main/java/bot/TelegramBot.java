package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import main.Config;
import logic.Logic;

/**
 * Класс реализации телеграмм бота
 */
public class TelegramBot extends TelegramLongPollingBot {
    final private String BOT_TOKEN = Config.getTelegramBotToken();
    final private String BOT_USERNAME = Config.getTelegramBotUsername();
    final private Logic botLogic = new Logic();

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    /**
     * Обработчик обновлений в боте
     * @param update - объект обработки обновлений
     */
    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()){
                Message inMess = update.getMessage();
                String chatId = inMess.getChatId().toString();
                String response = parseMessage(inMess.getText());

                SendMessage outMess = new SendMessage();
                outMess.setChatId(chatId);
                outMess.setText(response);
                execute(outMess);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Обработчик входящих сообщений
     * @param message сообщение от пользователя
     * @return ответ на сообщение
     */
    private String parseMessage(String message) {
        String response;
        try {
            response = botLogic.messageHandler(message);
        } catch (RuntimeException e) {
            response = "Произошла ошибка, попробуйте позже...";
            System.out.println(e.getMessage());
        }
        return response;
    }
}
