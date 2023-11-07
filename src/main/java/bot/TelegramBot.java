package bot;

import database.models.types.Plathform;
import database.services.UserService;
import logic.Response;
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


                SendMessage outMess = getResponse(inMess.getText(), currentUser);

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
    public SendMessage getResponse(String message, User currentUser) {
        SendMessage outMess = new SendMessage();
        Response response;
        try {
            response = handlersManager.getResponseFromHandler(message, currentUser);
            outMess.setChatId(currentUser.getPlathform_id());
            outMess.setText(response.message());
            outMess.setReplyMarkup(initKeyboard(response.keyboardMessages()));
        } catch (RuntimeException e) {
            outMess.setText("Произошла ошибка, попробуйте позже");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return outMess;
    }

    /**
     * Инициализация разметки клавиатуры для бота
     * @return Объект разметки клавиатуры
     */
    public ReplyKeyboardMarkup initKeyboard(List<String> keyboardText) {
        try {
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