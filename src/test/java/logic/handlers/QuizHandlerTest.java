package logic.handlers;

import database.models.Progquiz;
import database.models.User;
import database.models.types.Plathform;
import database.services.ProglangService;
import database.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * Тестирование обработчика квиза
 */
public class QuizHandlerTest {
    final List<Progquiz> questions = new ArrayList<>();
    UserService userService;
    ProglangService proglangService;

    @BeforeEach
    void setUp() {
        questions.add(new Progquiz("Какой метод используется для фильтрации массива?", "filter"));
        questions.add(new Progquiz("Какое ключевое слово используется для обозначения наследования классов?",
                "extends"));

        userService = Mockito.mock(UserService.class);
        proglangService = Mockito.mock(ProglangService.class);

        Mockito.when(proglangService.findProgquizzesByProglangId(1)).thenReturn(questions);
    }

    /**
     * Тестирование различных вариантов ответа
     */
    @Test
    void quizCommandsAndAnswersTest() {
        User testUser = new User(Plathform.TG, 0L);
        Mockito.doNothing().when(userService).update(testUser);

        QuizHandler quizHandler = new QuizHandler(proglangService , userService, 1);

        String quizCommand = quizHandler.getResponse("/quiz", testUser);
        Assertions.assertEquals(
                "Тест по ЯП JavaScript, состоит из 2 вопросов\n\nКакой метод используется для фильтрации массива?",
                quizCommand, "Проверка на команду /quiz");

        String questionRight = quizHandler.getResponse("filter", testUser);
        Assertions.assertEquals(
                "Вы ответили правильно!\nКакое ключевое слово используется для обозначения наследования классов?",
                questionRight, "Проверка на правильный ответ");

        String questionWrong = quizHandler.getResponse("fgdhgfdh", testUser);
        Assertions.assertEquals(
                "Вы ответили неправильно! Правильный ответ:extends\nТест закончен!",
                questionWrong, "Проверка на неправильный ответ и конец квиза");
    }

    /**
     * Тестирование команды /stop
     */
    @Test
    void stopCommand() {
        User testUser = new User(Plathform.TG, 0L);
        Mockito.doNothing().when(userService).update(testUser);

        QuizHandler quizHandler = new QuizHandler(proglangService , userService, 1);

        String quizCommand = quizHandler.getResponse("/quiz", testUser);
        Assertions.assertEquals(
                "Тест по ЯП JavaScript, состоит из 2 вопросов\n\nКакой метод используется для фильтрации массива?",
                quizCommand, "Проверка на команду /quiz");
        String stopCommand = quizHandler.getResponse("/stop", testUser);
        Assertions.assertEquals(
                "Тест завершен, чтобы начать заново введите /quiz",
                stopCommand, "Проверка на команду /stop");
    }
}
