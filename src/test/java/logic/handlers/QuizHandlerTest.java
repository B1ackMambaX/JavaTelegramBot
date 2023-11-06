package logic.handlers;

import database.dao.ProglangDao;
import database.models.Proglang;
import database.models.Progquiz;
import database.models.User;
import database.models.types.Plathform;
import database.services.ProglangService;
import database.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class QuizHandlerTest {

    @Test
    void answer() {
        User testUser1 = new User(Plathform.TG, 0L);
        User testUser2 = new User(Plathform.TG, 1L, "0");
        final List<Progquiz> questions = new ArrayList<>();

        questions.add(new Progquiz("Какой метод используется для фильтрации массива?", "filter"));
        questions.add(new Progquiz("Какое ключевое слово используется для обозначения наследования классов?",
                "extends"));

        UserService userService = Mockito.mock(UserService.class);
        ProglangService proglangService = Mockito.mock(ProglangService.class);
        ProglangDao proglangDao = Mockito.mock(ProglangDao.class);
        QuizHandler quizHandler = new QuizHandler(proglangService , userService, 1);


        Mockito.when(proglangService.findProgquizzesByProglangId(1)).thenReturn(questions);
        Mockito.when(proglangDao.findProgquizzesByProglangId(1)).thenReturn(questions);
        Mockito.doNothing().when(userService).update(testUser1);

        String quizCommand = quizHandler.getResponse("/quiz", testUser1);
        Assertions.assertEquals(
                "Тест по ЯП JavaScript, состоит из 2 вопросов\n\nКакой метод используется для фильтрации массива?",
                quizCommand, "Проверка на команду /quiz");

        String questionRight = quizHandler.getResponse("filter", testUser2);
        Assertions.assertEquals(
                "Вы ответили правильно!\nКакое ключевое слово используется для обозначения наследования классов?",
                questionRight, "Проверка на правильный ответ");

        String questionWrong = quizHandler.getResponse("fgdhgfdh", testUser1);
        Assertions.assertEquals(
                "Вы ответили неправильно! Правильный ответ:filter\nКакое ключевое слово используется для обозначения наследования классов?",
                questionWrong, "Проверка на неправильный ответ");
    }
}
