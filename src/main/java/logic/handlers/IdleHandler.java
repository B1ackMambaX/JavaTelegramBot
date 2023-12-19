package logic.handlers;

import logic.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Обработчик сообщений в сотоянии IDLE
 */
public class IdleHandler {
    /**
     * Получение ответа на сообщение пользователя в состоянии IDLE
     * @param message сообщение пользователя
     * @return ответ в зависимости от сообщения
     */
    public Response getResponse(String message) {
        message = message.toLowerCase();
        List<String> keyboardMessages = new ArrayList<>(List.of("/help", "/quiz", "/mystats", "/leaderboard"));

         if (message.equals("/start") || message.equals("/help")) {
            return new Response("""
                    Привет, я помогу тебе поднять теорию по языкам программирования!
                    Пока я умею не так много, но могу задать тебе вопросы по JavaSctipt.\s
                    В ответ на вопрос присылай мне одно слово.
                    Для начала введи /quiz
                    Чтобы остановить тест введи /stop""", keyboardMessages);
        } else {
            return new Response("Я не понимаю вас, посмотреть список доступных комманд можно с помощью /help",
                    keyboardMessages);
        }
    }
}
