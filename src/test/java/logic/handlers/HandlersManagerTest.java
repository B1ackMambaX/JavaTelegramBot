package logic.handlers;

import database.models.User;
import database.models.types.Plathform;
import database.models.types.State;
import database.services.UserService;
import logic.Response;
import logic.handlersManager.HandlersManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class HandlersManagerTest {
    private UserService userService;
    private IdleHandler idleHandler;
    private QuizHandler quizHandler;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        idleHandler = Mockito.mock(IdleHandler.class);
        quizHandler = Mockito.mock(QuizHandler.class);
    }

    /**
     * Проверка на переход пользователя в состояение QUIZ из состояния IDLE
     */
    @Test
    void testChangingStateToQuiz() {
        User testUser = new User(Plathform.TG, 0L, State.IDLE);
        Mockito.doNothing().when(userService).update(testUser);
        Mockito.when(quizHandler.getResponse("/quiz", testUser)).thenReturn(new Response("Выберите язык программирования", null));

        HandlersManager handlersManager = new HandlersManager(idleHandler, quizHandler, userService);
        Response response = handlersManager.getResponseFromHandler("/quiz", testUser);
        Assertions.assertEquals(testUser.getState(), State.QUIZ, "Тест на переход в состояние QUIZ");
    }

    /**
     * Проверка перехода пользователя в состояние IDLE из состояния QUIZ при команде /stop
     */
    @Test
    void testChangingStateToIdleOnStop() {
        User testUser = new User(Plathform.TG, 0L, State.QUIZ);
        Mockito.doNothing().when(userService).update(testUser);
        Mockito.when(quizHandler.getResponse("/stop", testUser)).thenReturn(
                new Response("Тест завершен, чтобы начать заново введите /quiz", null));

        HandlersManager handlersManager = new HandlersManager(idleHandler, quizHandler, userService);

        Response responseStop = handlersManager.getResponseFromHandler("/stop", testUser);
        Assertions.assertEquals(testUser.getState(), State.IDLE, "Тест на команду /stop");
    }

    /**
     * Проверка перехода пользователя в состояние IDLE из состояния QUIZ при конце квиза
     */
    @Test
    void testChangingStateToIdleOnQuizEnd() {
        User testUser = new User(Plathform.TG, 0L, State.QUIZ);
        Mockito.doNothing().when(userService).update(testUser);
        Mockito.when(quizHandler.getResponse("End test", testUser)).thenReturn(
                new Response("Вы ответили неправильно! Правильный ответ:split\nТест закончен!\n" +
                "Количество правильных ответов:4/5", null));

        HandlersManager handlersManager = new HandlersManager(idleHandler, quizHandler, userService);

        Response responseEndOfTest = handlersManager.getResponseFromHandler("End test", testUser);
        Assertions.assertEquals(testUser.getState(), State.IDLE, "Тест на конец квиза");
    }

    /**
     * Проверка отсутствия мутаций стейта во время квиза
     */
    @Test
    void testStateMutationDuringQuiz() {
        User testUser = new User(Plathform.TG, 0L, State.QUIZ);
        Mockito.doNothing().when(userService).update(testUser);
        Mockito.when(quizHandler.getResponse("extends", testUser)).thenReturn(
                new Response("Вы ответили правильно!\n" +
                        "Какой метод добавляет элемент в конец массива?", null));

        HandlersManager handlersManager = new HandlersManager(idleHandler, quizHandler, userService);
        Response responseEndOfTest = handlersManager.getResponseFromHandler("extends", testUser);
        Assertions.assertEquals(testUser.getState(), State.QUIZ, "Проверка отсутствия мутаций стейта во время квиза");
    }

    /**
     * Проверка отсутствия мутации стейта при неизвестной команде
     */
    @Test
    void testStateMutationWithUnknownCommand() {
        User testUser = new User(Plathform.TG, 0L, State.IDLE);
        Mockito.when(idleHandler.getResponse("/aboba")).thenReturn(
                new Response(
                        "Я не понимаю вас, посмотреть список доступных комманд можно с помощью /help",
                        null));

        HandlersManager handlersManager = new HandlersManager(idleHandler, quizHandler, userService);
        Response responseEndOfTest = handlersManager.getResponseFromHandler("/aboba", testUser);
        Assertions.assertEquals(testUser.getState(), State.IDLE,
                "Проверка отсутствия мутаций стейта при неизвестной команде");
    }
}
