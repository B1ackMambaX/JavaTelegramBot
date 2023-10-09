package logic;
import data.Question;
import data.Storage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class MessageParserUnitsTest {
    Logic logic;
    ArrayList<Question> questions;
    private final String startMessage = """
                    Привет, я помогу тебе поднять теорию по языкам программирования!
                    Пока я умею не так много, но могу задать тебе вопросы по JavaSctipt.\s
                    В ответ на вопрос присылай мне одно слово.
                    Для начала введи /quiz
                    Чтобы остановить тест введи /stop""";
    private final String unknownCommand = "Я не понимаю вас, посмотреть список доступных комманд можно с помощью /help";
    private final String stopCommand = "Тест завершен, чтобы начать заново введите /quiz";
    private final String rightAnswer = "Вы ответили правильно!\n";

    @Before
    public void setUp() {
        logic = new Logic();
        questions = new Storage().getAllQuestions();
    }

    @Test
    public void testParser() {
        Assert.assertEquals("/start command", startMessage, logic.messageHandler("/start"));
        Assert.assertEquals("unknown command", unknownCommand, logic.messageHandler("/some"));
        Assert.assertEquals("help command", startMessage, logic.messageHandler("/help"));
        Assert.assertEquals("starting quiz", "Тест по ЯП JavaScript, состоит из " + questions.size() + " вопросов\n\n" + questions.get(0).getQuestionText(), logic.messageHandler("/quiz"));
        Assert.assertEquals("stop command", stopCommand, logic.messageHandler("/stop"));
        Assert.assertEquals("starting quiz", "Тест по ЯП JavaScript, состоит из " + questions.size() + " вопросов\n\n" + questions.get(0).getQuestionText(), logic.messageHandler("/quiz"));
        for (int i = 0; i < questions.size() - 1; i++) {
            Assert.assertEquals("answer:" + i, questions.get(i).checkCorrectness(questions.get(i).getQuestionAnswer()) + questions.get(i + 1).getQuestionText(), logic.messageHandler(questions.get(i).getQuestionAnswer()));
        }
        Assert.assertEquals("help in quiz", startMessage, logic.messageHandler("/help"));
        Assert.assertEquals("final question", rightAnswer + "Тест закончен!", logic.messageHandler(questions.get(questions.size() - 1).getQuestionAnswer()));
    }
}
