package bot;

import database.models.types.Plathform;
import database.services.UserService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import main.Config;
import logic.handlersManager.HandlersManager;
import java.util.ArrayList;
import java.util.List;
import database.models.User;

/**
 * Класс реализации телеграмм бота
 */
public class TelegramBot extends TelegramLongPollingBot implements IBot {
    final private  Config config = new Config();
    final private String BOT_TOKEN = config.getTelegramBotToken();
    final private String BOT_USERNAME = config.getTelegramBotUsername();
    final private HandlersManager handlersManager = new HandlersManager();

    final private Plathform plathform = Plathform.TG;

    final private UserService userService = new UserService();


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
                long chatId = inMess.getChatId();
                User currentUser = userService.login(plathform, chatId);


                String response = getResponse(inMess.getText(), currentUser);
                SendMessage outMess = new SendMessage();
                outMess.setChatId(chatId);
                outMess.setText(response);
                outMess.setReplyMarkup(initKeyboard(currentUser));
                execute(outMess);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, который получает ответ на сообщение от пользователя
     * @param message сообщение
     * @param currentUser пользователь, от которого пришло сообщение
     * @return ответ на сообщение
     */
    public String getResponse(String message, User currentUser) {
        String response;
        try {
            response = handlersManager.getResponseFromHandler(message, currentUser);
        } catch (RuntimeException e) {
            response = "Произошла ошибка, попробуйте позже...";
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Инициализация разметки клавиатуры для бота
     * @return Объект разметки клавиатуры
     */
    public ReplyKeyboardMarkup initKeyboard(User currentUser) {
        try {
            String[] keyboardText = handlersManager.keyboardTextInitializer(currentUser);

            ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
            keyboard.setResizeKeyboard(true);
            keyboard.setOneTimeKeyboard(true);
            List<KeyboardRow> rows = new ArrayList<>();
            KeyboardRow row = new KeyboardRow();
            rows.add(row);

            for (String text : keyboardText) {
                row.add(new KeyboardButton(text));
            }
            keyboard.setKeyboard(rows);
            return keyboard;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}