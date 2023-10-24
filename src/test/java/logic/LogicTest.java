package logic;
import database.models.User;
import database.models.types.Plathform;
import logic.stateManager.StateManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Проверка ответа бота в зависимости от ответа пользователя
 */
public class LogicTest {
    private StateManager logic;
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
        logic = new StateManager();
        testUser = new User(Plathform.TG, (long) -1);
    }

    /**
     * Метод тестирующй логику бота
     */
    @Test
    public void testParser() {
        Assert.assertEquals("/start command", startMessage, logic.chooseHandler("/start", testUser));
        Assert.assertEquals("unknown command", unknownCommand, logic.chooseHandler("/some", testUser));
        Assert.assertEquals("help command", startMessage, logic.chooseHandler("/help", testUser));
        Assert.assertEquals("starting quiz", quizExpectedPhrases[0],
                logic.chooseHandler(quizMessageForParser[0], testUser));
        Assert.assertEquals("stop command", stopCommand, logic.chooseHandler("/stop", testUser));
        Assert.assertEquals("starting quiz", quizExpectedPhrases[0],
                logic.chooseHandler(quizMessageForParser[0], testUser));
        for (int i = 1; i < quizExpectedPhrases.length; i++) {
            Assert.assertEquals("answer:" + i, quizExpectedPhrases[i],
                    logic.chooseHandler(quizMessageForParser[i], testUser));
        }
    }
}
