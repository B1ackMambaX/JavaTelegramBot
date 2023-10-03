package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    final private String BOT_TOKEN = main.Config.getTelegramBotToken();
    final private String BOT_USERNAME = main.Config.getTelegramBotUsername();

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

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

    public String parseMessage(String textMsg) {
        String response = null;

        if (textMsg.equals("/start")) {
            response = "Привет, я помогу тебе поднять теорию по языкам программирования!";
        }
        else {
            response = "я тебя не понимать....";
        }

        return response;
    }
}
