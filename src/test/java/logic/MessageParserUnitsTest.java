package logic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Проверка ответа бота в зависимости от ответа пользователя
 */
public class MessageParserUnitsTest {
    private Logic logic;
    private final String[] quizExpectedPhrases = {
            "Тест по ЯП JavaScript, состоит из 10 вопросов\n\nКакой метод используется для фильтрации массива?",
            "Вы ответили правильно!\nКакое ключевое слово используется для обозначения наследования классов?",
            "Вы ответили неправильно! Правильный ответ:extends\nКакой метод добавляет элемент в конец массива?",
            "Вы ответили правильно!\nКакой метод удаляет последний элемент массива?",
            "Вы ответили правильно!\nКакой метод используется для разбиения строки на массив подстрок?",
            "Вы ответили правильно!\nКакой метод используется для объединения элементов массива в строку?",
            "Вы ответили правильно!\nКакой оператор используется для проверки на равенство с учетом типа данных?",
            "Вы ответили неправильно! Правильный ответ:===\nКакой оператор используется для логического И?",
            "Вы ответили правильно!\nКакой оператор используется для логического ИЛИ?",
            "Вы ответили правильно!\nКакой метод используется для поиска индекса элемента в массиве?",
            "Вы ответили правильно!\nТест закончен!"
    };

    private final String[] quizMessageForParser = {
            "/quiz",
            "filter",
            "hfhhfhf",
            "PUSH",
            "pop",
            "SpliT",
            "join",
            "==",
            "&&",
            "||",
            "indexOf"
    };
    private final String startMessage = """
                    Привет, я помогу тебе поднять теорию по языкам программирования!
                    Пока я умею не так много, но могу задать тебе вопросы по JavaSctipt.\s
                    В ответ на вопрос присылай мне одно слово.
                    Для начала введи /quiz
                    Чтобы остановить тест введи /stop""";
    private final String unknownCommand = "Я не понимаю вас, посмотреть список доступных комманд можно с помощью /help";
    private final String stopCommand = "Тест завершен, чтобы начать заново введите /quiz";

    @Before
    public void setUp() {
        logic = new Logic();
    }

    /**
     * Запуск теста
     */
    @Test
    public void testParser() {
        Assert.assertEquals("/start command", startMessage, logic.messageHandler("/start"));
        Assert.assertEquals("unknown command", unknownCommand, logic.messageHandler("/some"));
        Assert.assertEquals("help command", startMessage, logic.messageHandler("/help"));
        Assert.assertEquals("starting quiz", quizExpectedPhrases[0], logic.messageHandler(quizMessageForParser[0]));
        Assert.assertEquals("stop command", stopCommand, logic.messageHandler("/stop"));
        Assert.assertEquals("starting quiz", quizExpectedPhrases[0], logic.messageHandler(quizMessageForParser[0]));
        Assert.assertEquals("help in quiz", startMessage, logic.messageHandler("/help"));
        for (int i = 1; i < quizExpectedPhrases.length; i++) {
            Assert.assertEquals("answer:" + i, quizExpectedPhrases[i], logic.messageHandler(quizMessageForParser[i]));
        }
    }
}
