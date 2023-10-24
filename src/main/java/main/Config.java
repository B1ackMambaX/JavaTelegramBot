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

    public static String getDatabaseHost() { return dotenv.get("DB_HOST"); }

    public static String getDatabasePort() { return dotenv.get("DB_PORT"); }

    public static String getDatabaseName() { return dotenv.get("DB_NAME"); }

    public static String getDatabaseUser() { return dotenv.get("DB_USER"); }

    public static String getDatabasePass() { return dotenv.get("DB_PASS"); }
}
