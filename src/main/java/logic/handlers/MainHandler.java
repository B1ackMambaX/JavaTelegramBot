package logic.handlers;

/**
 * Обработчик основных сообщений
 */
public class MainHandler {
    /**
     * Обработчик сообщений пользователя в состоянии IDLE
     * @param message сообщение пользователя
     * @return ответ в зависимости от сообщения
     */
    public String messageHandler(String message) {
        message = message.toLowerCase();

         if (message.equals("/start") || message.equals("/help")) {
            return """
                    Привет, я помогу тебе поднять теорию по языкам программирования!
                    Пока я умею не так много, но могу задать тебе вопросы по JavaSctipt.\s
                    В ответ на вопрос присылай мне одно слово.
                    Для начала введи /quiz
                    Чтобы остановить тест введи /stop""";
        } else {
            return "Я не понимаю вас, посмотреть список доступных комманд можно с помощью /help";
        }
    }
}
