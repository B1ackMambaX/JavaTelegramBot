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
import java.util.logging.Logger;

import database.models.User;

/**
 * Класс реализации телеграмм бота
 */
public class TelegramBot extends TelegramLongPollingBot implements IBot {
    final private Config config = new Config();
    final private Logger logger = Logger.getLogger(TelegramBot.class.getName());
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
                String username = inMess.getFrom().getUserName();
                long chatId = inMess.getChatId();
                System.out.println(username);
                logger.info("Get new message from: " + chatId + " message: " + inMess);
                User currentUser = userService.login(plathform, chatId, username);

                Response response = getResponse(inMess.getText(), currentUser);
                SendMessage outMess = new SendMessage();

                ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
                keyboard.setResizeKeyboard(true);
                keyboard.setOneTimeKeyboard(true);
                List<KeyboardRow> rows = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();
                rows.add(row);

                for (String text : response.keyboardMessages()) {
                    row.add(new KeyboardButton(text));
                }
                keyboard.setKeyboard(rows);

                outMess.setReplyMarkup(keyboard);
                outMess.setChatId(chatId);
                outMess.setText(response.message());
                logger.info("Response to: " + chatId + ", message: " + outMess.getText());
                execute(outMess);
            }
        } catch (TelegramApiException e) {
            logger.severe(e.toString());
        }
    }

    /**
     * Метод, который получает ответ на сообщение от пользователя
     *
     * @param message     сообщение
     * @param currentUser пользователь, от которого пришло сообщение
     * @return ответ на сообщение
     */
    public Response getResponse(String message, User currentUser) {
        Logger logger = Logger.getLogger(TelegramBot.class.getName());
        Response response;
        try {
            response = handlersManager.getResponseFromHandler(message, currentUser);
        } catch (RuntimeException e) {
            List<String> keyboardMessages = new ArrayList<>();
            keyboardMessages.add("/help");
            keyboardMessages.add("/quiz");
            response = new Response("Произошла ошибка попробуйте позже", keyboardMessages);
            logger.severe(e.toString());
        }
        return response;
    }
}