package logic.handlers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестирование обработчика состояния IDLE
 */
class IdleHandlerTest {
    private IdleHandler idleHandler;
    @BeforeEach
    void setUp() {
        idleHandler = new IdleHandler();
    }

    /**
     * Команда /start
     */
    @Test
    void start() {
        String response = idleHandler.getResponse("/start");
        Assertions.assertEquals("""
                    Привет, я помогу тебе поднять теорию по языкам программирования!
                    Пока я умею не так много, но могу задать тебе вопросы по JavaSctipt.\s
                    В ответ на вопрос присылай мне одно слово.
                    Для начала введи /quiz
                    Чтобы остановить тест введи /stop""", response);
    }

    /**
     * Команда /help
     */
    @Test
    void help() {
        String response = idleHandler.getResponse("/help");
        Assertions.assertEquals("""
                    Привет, я помогу тебе поднять теорию по языкам программирования!
                    Пока я умею не так много, но могу задать тебе вопросы по JavaSctipt.\s
                    В ответ на вопрос присылай мне одно слово.
                    Для начала введи /quiz
                    Чтобы остановить тест введи /stop""", response);
    }

    /**
     * Неизвестная команда
     */
    @Test
    void unknownCommand() {
        String response = idleHandler.getResponse("/fhdhfjdshfd");
        Assertions.assertEquals("Я не понимаю вас, посмотреть список доступных комманд можно с помощью /help",
                response);
    }
}
