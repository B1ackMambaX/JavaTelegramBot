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
import org.mockito.Mock;
import org.mockito.Mockito;

public class HandlersManagerTest {
    private IdleHandler idleHandler;
    private QuizHandler quizHandler;
    private StatisticsHandler statisticsHandler;
    private HandlersManager handlersManager;

    @BeforeEach
    void setUp() {
        idleHandler = Mockito.mock(IdleHandler.class);
        quizHandler = Mockito.mock(QuizHandler.class);
        statisticsHandler = Mockito.mock(StatisticsHandler.class);
        Mockito.when(quizHandler.getResponse(Mockito.anyString(), Mockito.isA(User.class))).thenReturn(
                new Response("Message sended to QuizHandler", null));
        Mockito.when(idleHandler.getResponse(Mockito.anyString())).thenReturn(
                new Response("Message sended to IdleHandler", null));
        Mockito.when(statisticsHandler.getUserStatistic(Mockito.isA(User.class))).thenReturn(
                new Response("Message sended to StatisticHadnler.getUserStatistics", null));
        Mockito.when(statisticsHandler.getLeaderboard(Mockito.isA(User.class), Mockito.anyString())).thenReturn(
                new Response("Message sended to StatisticHadnler.getLeaderboard", null));
        handlersManager = new HandlersManager(idleHandler, quizHandler, statisticsHandler);
    }

    /**
     * Проверка отправления команды /quiz в QuizHandler из состояния IDLE
     */
    @Test
    void checkQuizCommandOnIDLE() {
        User testUser = new User(1, Plathform.TG, 0L, State.IDLE, "111");
        Response response = handlersManager.getResponseFromHandler("/quiz", testUser);
        Assertions.assertEquals(response.message(), "Message sended to QuizHandler");
    }

    /**
     * Проверка отправления команды /mystats в StatisticsHandler из состояния IDLE
     */
    @Test
    void checkMyStatCommandOnIDLE() {
        User testUser = new User(1, Plathform.TG, 0L, State.IDLE, "111");
        Response response = handlersManager.getResponseFromHandler("/mystats", testUser);
        Assertions.assertEquals(response.message(), "Message sended to StatisticHadnler.getUserStatistics");
    }

    /**
     * Проверка отправления команды /leaderboard в StatisticsHandler из состояния IDLE
     */
    @Test
    void checkLeaderboardCommandOnIDLE() {
        User testUser = new User(1, Plathform.TG, 0L, State.IDLE, "111");
        Response response = handlersManager.getResponseFromHandler("/leaderboard", testUser);
        Assertions.assertEquals(response.message(), "Message sended to StatisticHadnler.getLeaderboard");
    }

    /**
     * Проверка случаев отправки сообщения в IdleHandler
     */
    @Test
    void checkCommandsAndMessagesOnIDLE() {
        User testUser = new User(1, Plathform.TG, 0L, State.IDLE, "111");
        Response response = handlersManager.getResponseFromHandler("/start", testUser);
        Assertions.assertEquals(response.message(), "Message sended to IdleHandler");
        response = handlersManager.getResponseFromHandler("/help", testUser);
        Assertions.assertEquals(response.message(), "Message sended to IdleHandler");
        response = handlersManager.getResponseFromHandler("fnshfjdhs", testUser);
        Assertions.assertEquals(response.message(), "Message sended to IdleHandler");
    }

    /**
     * Проверка отправки сообщений в QuizHandler в состоянии QUIZ
     */
    @Test
    void checkMessagesOnQUIZ() {
        User testUser = new User(1, Plathform.TG, 0L, State.QUIZ, "111");
        Response response = handlersManager.getResponseFromHandler("JavaScript", testUser);
        Assertions.assertEquals(response.message(), "Message sended to QuizHandler");
    }

    /**
     * Проверка отправки сообщений в QuizHandler в состоянии QUIZ
     */
    @Test
    void checkMessagesOnLEADERBOARD() {
        User testUser = new User(1, Plathform.TG, 0L, State.LEADERBOARD, "111");
        Response response = handlersManager.getResponseFromHandler("JavaScript", testUser);
        Assertions.assertEquals(response.message(), "Message sended to StatisticHadnler.getLeaderboard");
    }
}
