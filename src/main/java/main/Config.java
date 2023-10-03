package main;

import io.github.cdimascio.dotenv.Dotenv;
public class Config {
    private static final Dotenv dotenv = Dotenv.load();
    public static String getTelegramBotToken() {
        return dotenv.get("TELEGRAM_BOT_TOKEN");
    }

    public static String getTelegramBotUsername() {
        return dotenv.get("TELEGRAM_BOT_USERNAME");
    }
}
