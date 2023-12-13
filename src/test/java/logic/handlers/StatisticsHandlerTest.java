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
    private StatisticHandler statisticHandler;

    @BeforeEach
    void setUp() {
        statisticsDao = Mockito.mock(StatisticsDao.class);
        proglangDao = Mockito.mock(ProglangDao.class);
        userService = Mockito.mock(UserService.class);
    }

    /**
     * Проверка случая, когда у пользователя нет статистики
     */
    @Test
    void testEmptyStatistics() {
        User testUser = new User(Plathform.TG, 1L, State.IDLE, "b1ackmambax");
        Mockito.when(statisticsDao.findAllByUserId(1)).thenReturn(new ArrayList<Statistics>());
        statisticHandler = new StatisticHandler(statisticsDao, proglangDao, userService);
        Response response = statisticHandler.getUserStatistic(testUser);

        Assertions.assertEquals(response.message(), "Статистика не найдена");
    }

    /**
     * Проверка вывода статистики
     */
    @Test
    void testStatistics() {
        User testUser = new User(Plathform.TG, 1L, State.IDLE, "b1ackmambax");
        List<Statistics> testStatistics = new ArrayList<>();
        testStatistics.add(new Statistics(testUser, new Proglang("JavaScript", 1), 3));
        testStatistics.add(new Statistics(testUser, new Proglang("Python", 2), 5));

        Mockito.when(statisticsDao.findAllByUserId(1)).thenReturn(testStatistics);
        Mockito.when(proglangDao.countProgquizzesByProglangId(1)).thenReturn(5L);
        Mockito.when(proglangDao.countProgquizzesByProglangId(2)).thenReturn(5L);
        statisticHandler = new StatisticHandler(statisticsDao, proglangDao, userService);
        Response response = statisticHandler.getUserStatistic(testUser);

        Assertions.assertEquals(response.message(), "Ваша статистика:\n" +
                "JavaScript: 3/5\n" +
                "Python: 5/5\n" +
                "Общая: 8/10\n");
    }
}
