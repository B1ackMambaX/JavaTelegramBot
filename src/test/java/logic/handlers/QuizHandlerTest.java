package logic.handlers;

import database.dao.StatisticsDao;
import database.models.*;
import database.models.types.Plathform;
import database.models.types.State;
import database.services.ProglangService;
import database.services.UserService;
import logic.Response;
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
    UserService userService;
    ProglangService proglangService;
    StatisticsDao statisticsDao;
    List<Progquiz> questions = new ArrayList<>();
    List<String> proglangNames = new ArrayList<>();

    @BeforeEach
    void setUp() {
        questions.add(new Progquiz("Какой метод используется для фильтрации массива?", "filter"));
        questions.add(new Progquiz("Какое ключевое слово используется для обозначения наследования классов?",
                "extends"));
        proglangNames.add("JavaScript");
        proglangNames.add("Python");

        userService = Mockito.mock(UserService.class);
        proglangService = Mockito.mock(ProglangService.class);
        statisticsDao = Mockito.mock(StatisticsDao.class);

        Mockito.when(proglangService.getQuestionByLang(1, 0)).thenReturn(questions.get(0));
        Mockito.when(proglangService.getQuestionByLang(1, 1)).thenReturn(questions.get(1));
        Mockito.when(proglangService.getAllProglangNames()).thenReturn(proglangNames);
        Mockito.when(proglangService.getProglangIdByName("javascript")).thenReturn(1);
        Mockito.when(proglangService.getProglangIdByName("aboba")).thenReturn(-1);
        Mockito.when(proglangService.findProglang(1)).thenReturn(new Proglang("JavaScript", 1));
        Mockito.when(proglangService.getSizeOfProglang(1)).thenReturn(2L);
        Mockito.when(statisticsDao.findByProglangIdAndUserId(1, 1)).thenReturn(new Statistics());
        Mockito.doNothing().when(statisticsDao).save(new Statistics());
        Mockito.doNothing().when(statisticsDao).update(new Statistics());
    }

    /**
     * Тестирование различных вариантов ответа и выбора ЯП
     */
    @Test
    void answer() {
        User testUser = new User(1, Plathform.TG, 0L, State.QUIZ, "111");
        Quizstate quizstate = new Quizstate(testUser);

        Mockito.doNothing().when(userService).update(testUser);
        Mockito.when(userService.getQuizState(testUser.getId())).thenReturn(quizstate);
        Mockito.doNothing().when(userService).updateQuizState(quizstate);
        QuizHandler quizHandler = new QuizHandler(proglangService, userService, quizstate, statisticsDao);


        Response quizCommand = quizHandler.getResponse("/quiz", testUser);
        Assertions.assertEquals(
                "Выберите язык программирования",
                quizCommand.message(), "Проверка на команду /quiz");

        Response wrongLang = quizHandler.getResponse("aboba", testUser);
        Assertions.assertEquals("Язык программирования не найден", wrongLang.message(),
                "Проверка на неизвестный ЯП");

        Response chooseJs = quizHandler.getResponse("JavaScript", testUser);
        Assertions.assertEquals(
                "Тест по ЯП JavaScript, состоит из 2 вопросов\n\nКакой метод используется для фильтрации массива?",
                chooseJs.message(), "Выбор ЯП");
        Assertions.assertEquals(1, quizstate.getCurrentProglangId(), "Проверка на смену ЯП у юзера");

        Response questionRight = quizHandler.getResponse("filter", testUser);
        Assertions.assertEquals(
                "Вы ответили правильно!\nКакое ключевое слово используется для обозначения наследования классов?",
                questionRight.message(), "Проверка на правильный ответ");

        Response questionWrong = quizHandler.getResponse("hfdjhf", testUser);
        Assertions.assertEquals("Вы ответили неправильно! Правильный ответ:extends\nТест закончен!\nКоличество правильных ответов:1/2",
                questionWrong.message(), "Проверка на неправильный ответ + конец + статистика");
    }

    /**
     * Тестирование разметки клавиатуры в различных ситуациях
     */
    @Test
    void keyboardMarkup() {
        List<String> keyboardStop = new ArrayList<>();
        keyboardStop.add("/stop");

        List<String> keyboardLangs = new ArrayList<>();
        keyboardLangs.add("JavaScript");
        keyboardLangs.add("Python");

        List<String> keyboardFinal = new ArrayList<>();
        keyboardFinal.add("/help");
        keyboardFinal.add("/quiz");
        keyboardFinal.add("/mystats");
        keyboardFinal.add("/leaderboard");

        User testUser = new User(1, Plathform.TG, 0L, State.QUIZ, "111");
        Quizstate quizstate = new Quizstate(testUser);

        Mockito.doNothing().when(userService).update(testUser);
        Mockito.when(userService.getQuizState(testUser.getId())).thenReturn(quizstate);
        Mockito.doNothing().when(userService).updateQuizState(quizstate);
        QuizHandler quizHandler = new QuizHandler(proglangService, userService, quizstate, statisticsDao);

        Response quizCommand = quizHandler.getResponse("/quiz", testUser);
        Assertions.assertEquals(
                keyboardLangs,
                quizCommand.keyboardMessages(), "Проверка клавиатуры на команду /quiz");

        Response wrongLang = quizHandler.getResponse("aboba", testUser);
        Assertions.assertEquals(keyboardLangs, wrongLang.keyboardMessages(),
                "Проверка клавиатуры на неизвестный ЯП");

        Response chooseJs = quizHandler.getResponse("JavaScript", testUser);
        Assertions.assertEquals(keyboardStop, chooseJs.keyboardMessages()
                ,"Проверка клавиатуры при выборе ЯП");

        Response questionRight = quizHandler.getResponse("filter", testUser);
        Assertions.assertEquals(keyboardStop,
                questionRight.keyboardMessages(), "Проверка клавиатуры на правильный ответ");

        Response questionWrong = quizHandler.getResponse("hfdjhf", testUser);
        Assertions.assertEquals(keyboardFinal, questionWrong.keyboardMessages(),
                "Проверка на неправильный ответ + конец + статистика");
    }
}

