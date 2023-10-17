package logic.handlers;

/**
 * Класс реализующий логику работы бота
 */
public class MainHandler {
    /**
     * Обработчик сообщений пользователя
     * @param message сообщение пользователя
     * @return ответ в зависимости от сообщения
     */
    public static String messageHandler(String message) {
        message = message.toLowerCase();

         if (message.equals("/start") || message.equals("/help")) {
            return """
                    Привет, я помогу тебе поднять теорию по языкам программирования!
                    Пока я умею не так много, но могу задать тебе вопросы по JavaSctipt.\s
                    В ответ на вопрос присылай мне одно слово.
                    Для начала введи /quiz
                    Чтобы остановить тест введи /stop""";
        } if (message.equals("/stop")) {
            return "Тест завершен, чтобы начать заново введите /quiz";
        } else {
            return "Я не понимаю вас, посмотреть список доступных комманд можно с помощью /help";
        }
    }
}
