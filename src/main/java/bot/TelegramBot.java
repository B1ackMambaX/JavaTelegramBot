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
import logic.stateManager.StateManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import database.models.User;

/**
 * Класс реализации телеграмм бота
 */
public class TelegramBot extends TelegramLongPollingBot implements IBot {
    final private String BOT_TOKEN = Config.getTelegramBotToken();
    final private String BOT_USERNAME = Config.getTelegramBotUsername();
    final private StateManager stateManager = new StateManager();

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
                User currentUser = login(chatId);


                String response = parseMessage(inMess.getText(), currentUser);
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
     * Обработчик входящих сообщений
     *
     * @param message сообщение от пользователя
     * @return ответ на сообщение
     */

    public String parseMessage(String message, User currentUser) {
        String response;
        try {
            response = stateManager.chooseHandler(message, currentUser);
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
            String[] keyboardText = stateManager.keyboardTextInitializer(currentUser);

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

    /**
     * Добавление нового пользователя либо получение уже существующего
     * @param chatId id чата
     * @return пользователь
     */
    public User login(long chatId) {
        User currentUser = userService.findUserByPlathformAndId(this.plathform, chatId);
        if(currentUser == null) {
            // TODO: Допилить метод регистрации пользователя
            currentUser = new User(this.plathform, chatId);
            userService.saveUser(currentUser);
        }
        return currentUser;
    }
}