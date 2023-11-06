package logic;
import database.models.User;
import database.models.types.Plathform;
import logic.handlersManager.handlersManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Проверка ответа бота в зависимости от ответа пользователя
 */
public class LogicTest {
    private handlersManager logic;
    private User testUser;
    private final String[] quizExpectedPhrases = {
            "Тест по ЯП JavaScript, состоит из 10 вопросов\n\nКакой метод используется для фильтрации массива?",
            "Вы ответили правильно!\nКакое ключевое слово используется для обозначения наследования классов?",
            "Вы ответили неправильно! Правильный ответ:extends\nКакой метод добавляет элемент в конец массива?",
    };

    private final String[] quizMessageForParser = {
            "/quiz",
            "filter",
            "hfhhfhf",
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
        logic = new handlersManager();
        testUser = new User(Plathform.TG, (long) -1);
    }

    /**
     * Метод тестирующй логику бота
     */
    @Test
    public void testParser() {
        Assert.assertEquals("Ответ бота на команду /start", startMessage, logic.getResponseFromHandler("/start", testUser));
        Assert.assertEquals("Ответ бота на неизвестную команду", unknownCommand, logic.getResponseFromHandler("/some", testUser));
        Assert.assertEquals("Ответ бота на команду /help", startMessage, logic.getResponseFromHandler("/help", testUser));
        Assert.assertEquals("Ответ бота на команду /quiz", quizExpectedPhrases[0],
                logic.getResponseFromHandler(quizMessageForParser[0], testUser));
        Assert.assertEquals("Ответ бота на команду /stop", stopCommand, logic.getResponseFromHandler("/stop", testUser));
        Assert.assertEquals("Ответ бота на команду /quiz", quizExpectedPhrases[0],
                logic.getResponseFromHandler(quizMessageForParser[0], testUser));
        for (int i = 1; i < quizExpectedPhrases.length; i++) {
            Assert.assertEquals("Ответ бота на вопрос " + i + " ", quizExpectedPhrases[i],
                    logic.getResponseFromHandler(quizMessageForParser[i], testUser));
        }
    }
}
