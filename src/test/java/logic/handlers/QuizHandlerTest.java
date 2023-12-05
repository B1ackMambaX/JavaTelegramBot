package logic.handlers;

import database.models.Proglang;
import database.models.Progquiz;
import database.models.Quizstate;
import database.models.User;
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
    List<Progquiz> questions = new ArrayList<>();
    List<String> proglangNames = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Proglang javascript = new Proglang("JavaScript", 1);
        Proglang python = new Proglang("Python", 2);
        questions.add(new Progquiz("Какой метод используется для фильтрации массива?",
                "filter", javascript));
        questions.add(new Progquiz("Какое ключевое слово используется для обозначения наследования классов?",
                "extends", javascript));
        questions.add(new Progquiz("Какое ключевое слово используется для объявления функции в Python?",
                "def", python));
        questions.add(new Progquiz("Какой тип данных используется для хранения неизменяемых последовательностей в Python?",
                "tuple", python));

        proglangNames.add("JavaScript");
        proglangNames.add("Python");

        userService = Mockito.mock(UserService.class);
        proglangService = Mockito.mock(ProglangService.class);

        Mockito.when(proglangService.getQuestionByLang(1, 0)).thenReturn(questions.get(0));
        Mockito.when(proglangService.getQuestionByLang(1, 1)).thenReturn(questions.get(1));
        Mockito.when(proglangService.getQuestionByLang(2, 0)).thenReturn(questions.get(2));
        Mockito.when(proglangService.getQuestionByLang(2, 1)).thenReturn(questions.get(3));
        Mockito.when(proglangService.getAllProglangNames()).thenReturn(proglangNames);
        Mockito.when(proglangService.getProglangIdByName("javascript")).thenReturn(1);
        Mockito.when(proglangService.getProglangIdByName("python")).thenReturn(2);
        Mockito.when(proglangService.getProglangIdByName("aboba")).thenReturn(-1);
        Mockito.when(proglangService.findProglang(1)).thenReturn(javascript);
        Mockito.when(proglangService.findProglang(2)).thenReturn(python);
        Mockito.when(proglangService.getSizeOfProglang(1)).thenReturn(2L);
        Mockito.when(proglangService.getSizeOfProglang(2)).thenReturn(2L);
    }

    /**
     * Тестирование различных вариантов ответа и выбора ЯП
     */
    @Test
    void answer() {
        User testUser = new User(Plathform.TG, 0L, State.QUIZ);
        Quizstate quizstate = new Quizstate(testUser);

        Mockito.doNothing().when(userService).update(testUser);
        Mockito.when(userService.getQuizState(testUser.getId())).thenReturn(quizstate);
        Mockito.doNothing().when(userService).updateQuizState(quizstate);
        QuizHandler quizHandler = new QuizHandler(proglangService, userService, quizstate);


        Response quizCommand = quizHandler.getResponse("/quiz", testUser);
        Assertions.assertEquals(
                "Выберите язык программирования",
                quizCommand.message(), "Проверка на команду /quiz");
        Assertions.assertEquals(quizstate.getCurrentProglangId(), 0,
                "Проверка на изменения ID ЯП в состоянии");

        Response wrongLang = quizHandler.getResponse("aboba", testUser);
        Assertions.assertEquals("Язык программирования не найден", wrongLang.message(),
                "Проверка на неизвестный ЯП");
        Assertions.assertEquals(quizstate.getCurrentProglangId(), 0,
                "Проверка на изменения ID ЯП в состоянии при неправильном выборе языка");

        Response chooseJs = quizHandler.getResponse("JavaScript", testUser);
        Assertions.assertEquals(
                "Тест по ЯП JavaScript, состоит из 2 вопросов\n\nКакой метод используется для фильтрации массива?",
                chooseJs.message(), "Выбор ЯП");
        Assertions.assertEquals(quizstate.getCurrentProglangId(), 1, "Проверка на смену ЯП у юзера");
        Assertions.assertEquals(quizstate.getCurrentQuestionIndex(), 0,
                "Проверка на изменение текущего вопроса");

        Response questionRight = quizHandler.getResponse("filter", testUser);
        Assertions.assertEquals(
                "Вы ответили правильно!\nКакое ключевое слово используется для обозначения наследования классов?",
                questionRight.message(), "Проверка на правильный ответ");
        Assertions.assertEquals(quizstate.getCurrentQuestionIndex(), 1,
                "Проверка на изменение текущего вопроса");

        Response questionWrong = quizHandler.getResponse("hfdjhf", testUser);
        Assertions.assertEquals("Вы ответили неправильно! Правильный ответ:extends\nТест закончен!\nКоличество правильных ответов:1/2",
                questionWrong.message(), "Проверка на неправильный ответ + конец + статистика");
        Assertions.assertEquals(quizstate.getCurrentQuestionIndex(), -1,
                "Проверка на сброс значения текущего вопроса");
        Assertions.assertEquals(quizstate.getCurrentProglangId(), -1,
                "Проверка на сброс значения текущего ЯП");
        Assertions.assertEquals(quizstate.getCurrentQuizStats(), -1,
                "Проверка на сброс значения текущего ЯП");
    }

    @Test
    void chooseAnotherProglangAndStopCommand() {
        User testUser = new User(Plathform.TG, 0L, State.QUIZ);
        Quizstate quizstate = new Quizstate(testUser);

        Mockito.doNothing().when(userService).update(testUser);
        Mockito.when(userService.getQuizState(testUser.getId())).thenReturn(quizstate);
        Mockito.doNothing().when(userService).updateQuizState(quizstate);
        QuizHandler quizHandler = new QuizHandler(proglangService, userService, quizstate);

        Response quizCommand = quizHandler.getResponse("/quiz", testUser);
        Assertions.assertEquals(
                "Выберите язык программирования",
                quizCommand.message(), "Проверка на команду /quiz");
        Assertions.assertEquals(quizstate.getCurrentProglangId(), 0,
                "Проверка на изменения ID ЯП в состоянии");

        Response choosePython = quizHandler.getResponse("Python", testUser);
        Assertions.assertEquals(
                "Тест по ЯП Python, состоит из 2 вопросов\n\n" +
                        "Какое ключевое слово используется для объявления функции в Python?",
                choosePython.message(), "Выбор ЯП");
        Assertions.assertEquals(quizstate.getCurrentProglangId(),2 , "Проверка на смену ЯП у юзера");
        Assertions.assertEquals(quizstate.getCurrentQuestionIndex(), 0,
                "Проверка на изменение текущего вопроса");

        Response stopCommand = quizHandler.getResponse("/stop", testUser);

        Assertions.assertEquals(stopCommand.message(), "Тест завершен, чтобы начать заново введите /quiz");

        Assertions.assertEquals(quizstate.getCurrentQuestionIndex(), -1,
                "Проверка на сброс значения текущего вопроса");
        Assertions.assertEquals(quizstate.getCurrentProglangId(), -1,
                "Проверка на сброс значения текущего ЯП");
        Assertions.assertEquals(quizstate.getCurrentQuizStats(), -1,
                "Проверка на сброс значения текущего ЯП");
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

        User testUser = new User(Plathform.TG, 0L, State.QUIZ);
        Quizstate quizstate = new Quizstate(testUser);

        Mockito.doNothing().when(userService).update(testUser);
        Mockito.when(userService.getQuizState(testUser.getId())).thenReturn(quizstate);
        Mockito.doNothing().when(userService).updateQuizState(quizstate);
        QuizHandler quizHandler = new QuizHandler(proglangService, userService, quizstate);

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
                questionRight.keyboardMessages(), "Проверка клавиатуры в процессе квиза");

        Response questionWrong = quizHandler.getResponse("hfdjhf", testUser);
        Assertions.assertEquals(keyboardFinal, questionWrong.keyboardMessages(),
                "Проверка клавиатуры в конце квиза");
    }
}