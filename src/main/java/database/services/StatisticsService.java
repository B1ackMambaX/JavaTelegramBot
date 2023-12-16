package database.services;

import database.models.Proglang;
import database.models.Statistics;
import database.models.User;
import database.dao.StatisticsDao;

/**
 * Сервис для работы со статистикой
 */
public class StatisticsService {
    private final StatisticsDao statisticsDao;
    public StatisticsService() {
        statisticsDao = new StatisticsDao();
    }

    /**
     * Сохранение статистики в конце квиза
     * @param user объект пользователя
     * @param count счетчик статистики
     * @param proglang объект ЯП
     */
    public void saveStatistics(User user, int count, Proglang proglang) {
        try {
            Statistics statistics = statisticsDao.findByProglangIdAndUserId(proglang.getId(), user.getId());
            if (statistics.getScore() > count) {
                return;
            }
            statistics.setScore(count);
            statisticsDao.update(statistics);
        } catch (Exception e) {
            Statistics statistics = new Statistics(user, proglang, count);
            statisticsDao.save(statistics);
        }
    }
}
