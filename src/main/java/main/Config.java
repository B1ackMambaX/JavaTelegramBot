package main;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Конфиг файл позволяющий получить основные переменные
 */
public class Config {
    private final Dotenv dotenv = Dotenv.load();
    public String getTelegramBotToken() {
        return dotenv.get("TELEGRAM_BOT_TOKEN");
    }

    public  String getTelegramBotUsername() {
        return dotenv.get("TELEGRAM_BOT_USERNAME");
    }

    public String getDatabaseHost() { return dotenv.get("DB_HOST"); }

    public String getDatabasePort() { return dotenv.get("DB_PORT"); }

    public String getDatabaseName() { return dotenv.get("DB_NAME"); }

    public String getDatabaseUser() { return dotenv.get("DB_USER"); }

    public String getDatabasePass() { return dotenv.get("DB_PASS"); }
}
