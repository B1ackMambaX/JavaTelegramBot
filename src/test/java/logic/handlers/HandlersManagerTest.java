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

    private StatisticsHandler statisticsHandler;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        idleHandler = Mockito.mock(IdleHandler.class);
        quizHandler = Mockito.mock(QuizHandler.class);
        statisticsHandler = Mockito.mock(StatisticsHandler.class);
    }

    /**
     * Проверка на переход в состояение QUIZ из состояния IDLE
     */
    @Test
    void testChangingStateToQuiz() {
        User testUser = new User(1, Plathform.TG, 0L, State.IDLE, "111");
        Mockito.doNothing().when(userService).update(testUser);
        Mockito.when(idleHandler.getResponse("/quiz")).thenReturn(new Response("123", null));
        Mockito.when(quizHandler.getResponse("/quiz", testUser)).thenReturn(new Response("123", null));

        HandlersManager handlersManager = new HandlersManager(idleHandler, quizHandler, userService, statisticsHandler);
        Response response = handlersManager.getResponseFromHandler("/quiz", testUser);
        Assertions.assertEquals(testUser.getState(), State.QUIZ, "Тест на переход в состояние QUIZ");
    }

    /**
     * Проверка перехода в состояние IDLE из состояния QUIZ при команде /stop либо конце квиза
     */
    @Test
    void testChangingStateToIdle() {
        User testUser = new User(1, Plathform.TG, 0L, State.QUIZ, "111");
        Mockito.doNothing().when(userService).update(testUser);
        Mockito.when(idleHandler.getResponse("/stop")).thenReturn(new Response("123", null));
        Mockito.when(quizHandler.getResponse("/stop", testUser)).thenReturn(new Response("123", null));
        Mockito.when(quizHandler.getResponse("End test", testUser)).thenReturn(new Response("Тест закончен!", null));

        HandlersManager handlersManager = new HandlersManager(idleHandler, quizHandler, userService, statisticsHandler);

        Response responseStop = handlersManager.getResponseFromHandler("/stop", testUser);
        Assertions.assertEquals(testUser.getState(), State.IDLE, "Тест на команду /stop");

        testUser.setState(State.QUIZ);

        Response responseEndOfTest = handlersManager.getResponseFromHandler("End test", testUser);
        Assertions.assertEquals(testUser.getState(), State.IDLE, "Тест на конец квиза");
    }

    @Test
    void testChangingStateToLeaderboard() {
        User testUser = new User(1, Plathform.TG, 0L, State.IDLE, "111");
        Mockito.doNothing().when(userService).update(testUser);
        Mockito.when(statisticsHandler.getUserStatistic(testUser)).thenReturn(new Response("Ваша статистика:\n" +
                "JavaScript: 5/5\n" +
                "Python: 2/5\n" +
                "Общая: 7/10", null));

        HandlersManager handlersManager = new HandlersManager(idleHandler, quizHandler, userService, statisticsHandler);

        Response responseLeaderboard = handlersManager.getResponseFromHandler("/leaderboard", testUser);
        Assertions.assertEquals(testUser.getState(), State.LEADERBOARD, "Тест на команду /leaderboard");
    }
}
