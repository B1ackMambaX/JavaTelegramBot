package logic.handlers;

import database.dao.ProglangDao;
import database.dao.StatisticsDao;
import database.models.Proglang;
import database.models.Statistics;
import database.models.User;
import database.models.types.Plathform;
import database.models.types.State;
import database.services.UserService;
import logic.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class StatisticsHandlerTest {
    private StatisticsDao statisticsDao;
    private  ProglangDao proglangDao;
    private UserService userService;
    private StatisticsHandler statisticsHandler;

    @BeforeEach
    void setUp() {
        statisticsDao = Mockito.mock(StatisticsDao.class);
        proglangDao = Mockito.mock(ProglangDao.class);
        userService = Mockito.mock(UserService.class);

        Mockito.when(proglangDao.getIdByName("python")).thenReturn(2L);
        Mockito.when(proglangDao.getIdByName("javascript")).thenReturn(1L);
        Mockito.when(proglangDao.countProgquizzesByProglangId(1)).thenReturn(5L);
        Mockito.when(proglangDao.countProgquizzesByProglangId(2)).thenReturn(5L);
    }

    /**
     * Проверка случая, когда у пользователя нет статистики
     */
    @Test
    void testEmptyStatistics() {
        User testUser = new User(1, Plathform.TG, 1L, State.IDLE, "b1ackmambax");
        Mockito.when(statisticsDao.findAllByUserId(1)).thenReturn(new ArrayList<Statistics>());

        statisticsHandler = new StatisticsHandler(statisticsDao, proglangDao, userService);
        Response response = statisticsHandler.getUserStatistic(testUser);

        Assertions.assertEquals(response.message(), "Статистика не найдена");
    }

    /**
     * Проверка вывода статистики
     */
    @Test
    void testStatistics() {
        User testUser = new User(1, Plathform.TG, 1L, State.IDLE, "b1ackmambax");
        List<Statistics> testStatistics = new ArrayList<>();
        testStatistics.add(new Statistics(testUser, new Proglang("JavaScript", 1), 3));
        testStatistics.add(new Statistics(testUser, new Proglang("Python", 2), 5));

        Mockito.when(statisticsDao.findAllByUserId(1)).thenReturn(testStatistics);

        statisticsHandler = new StatisticsHandler(statisticsDao, proglangDao, userService);
        Response response = statisticsHandler.getUserStatistic(testUser);

        Assertions.assertEquals(response.message(), "Ваша статистика:\n" +
                "JavaScript: 3/5\n" +
                "Python: 5/5\n" +
                "Общая: 8/10\n");
    }

    /**
     * Проверка вывода статистики
     */
    @Test
    void testLeaderboard() {
        User testUser = new User(1, Plathform.TG, 1L, State.LEADERBOARD, "b1ackmambax");
        User statUser1 = new User(2, Plathform.TG, 2L, State.IDLE, "begenFys");
        User statUser2 = new User(3, Plathform.TG, 3L, State.IDLE, "sssenji");
        Proglang js = new Proglang("JavaScript", 1);

        List<Statistics> testStatistics = new ArrayList<>();
        testStatistics.add(new Statistics(testUser, js, 3));
        testStatistics.add(new Statistics(statUser1, js, 5));
        testStatistics.add(new Statistics(statUser2, js, 2));

        List<String> proglangNames = new ArrayList<>();
        proglangNames.add("JavaScript");
        proglangNames.add("Python");

        Mockito.when(proglangDao.getAllNames()).thenReturn(proglangNames);
        Mockito.when(statisticsDao.findAllByProglangId(1)).thenReturn(testStatistics);
        Mockito.when(statisticsDao.findAllByProglangId(2)).thenReturn(new ArrayList<>());
        Mockito.doNothing().when(userService).update(testUser);

        statisticsHandler = new StatisticsHandler(statisticsDao, proglangDao, userService);

        Response responseCommand = statisticsHandler.getLeaderboard(testUser, "/leaderboard");
        Assertions.assertEquals(responseCommand.message(), "Выберите ЯП:");
        Response chooseUnknownLang = statisticsHandler.getLeaderboard(testUser, "C++");
        Assertions.assertEquals(chooseUnknownLang.message(), "Язык не найден");
        Response statistics = statisticsHandler.getLeaderboard(testUser, "JavaScript");
        Assertions.assertEquals(statistics.message(), "Топ по языку JavaScript\n" +
                "1. begenFys 5/5\n" +
                "2. b1ackmambax 3/5\n" +
                "3. sssenji 2/5\n");
        testUser.setState(State.LEADERBOARD);
        Response noLeaderboard = statisticsHandler.getLeaderboard(testUser, "Python");
        Assertions.assertEquals(noLeaderboard.message(), "Нет статистики по выбранному ЯП");
    }
}
