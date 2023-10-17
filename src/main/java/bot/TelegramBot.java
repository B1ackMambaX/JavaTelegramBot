package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import main.Config;
import logic.stateManager.StateManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс реализации телеграмм бота
 */
public class TelegramBot extends TelegramLongPollingBot {
    final private String BOT_TOKEN = Config.getTelegramBotToken();
    final private String BOT_USERNAME = Config.getTelegramBotUsername();
    final private StateManager stateManager = new StateManager();


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
     *
     * @param update - объект обработки обновлений
     */
    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message inMess = update.getMessage();
                String chatId = inMess.getChatId().toString();
                String response = parseMessage(inMess.getText());

                SendMessage outMess = new SendMessage();
                outMess.setChatId(chatId);
                outMess.setText(response);
                outMess.setReplyMarkup(initKeyboard());
                execute(outMess);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Обработчик входящих сообщений
     *
     * @param message сообщение от пользователя
     * @return ответ на сообщение
     */
    private String parseMessage(String message) {
        String response;
        try {
            response = stateManager.chooseHandler(message);
        } catch (RuntimeException e) {
            response = "Произошла ошибка, попробуйте позже...";
            System.out.println(e.getMessage());
        }
        return response;
    }

    /**
     * Инициализация разметки клавиатуры для бота
     * @return Объект разметки клавиатуры
     */
    private ReplyKeyboardMarkup initKeyboard() {
        String[] keyboardText = stateManager.keyboardTextInitializer();

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        rows.add(row);

        for (String text : keyboardText) {
            row.add(new KeyboardButton(text));
        }
        keyboard.setKeyboard(rows);
        return keyboard;
    }
}