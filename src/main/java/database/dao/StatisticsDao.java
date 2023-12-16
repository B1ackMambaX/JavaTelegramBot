package database.dao;

import database.models.Statistics;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object для таблицы статистики
 */
public class StatisticsDao extends BaseDao<Statistics> {
    public StatisticsDao() {
        super(Statistics.class);
    }

    /**
     * Найти всю статистику по языку программирования по его id в БД
     * @param proglangId id ЯП в БД
     * @return статистики
     */
    public List<Statistics> findAllByProglangId(long proglangId) {
        return new ArrayList<>(processSession(session -> session.createQuery(
                        "select s " +
                                "from Statistics s " +
                                "where s.proglang.id = :proglangId " +
                                "order by s.score desc",
                        Statistics.class)
                .setParameter("proglangId", proglangId)
                .setMaxResults(10)
                .getResultList()));
    }

    /**
     * Найти всю статистику по пользователю по его id в БД
     * @param userId id ЯП в БД
     * @return статистики
     */
    public List<Statistics> findAllByUserId(long userId) {
        return new ArrayList<>(processSession(session -> session.createQuery(
                        "select s " +
                                "from Statistics s " +
                                "where s.user.id = :userId",
                        Statistics.class)
                .setParameter("userId", userId)
                .getResultList()));
    }

    /**
     * Получение статистики пользователя по одному из ЯП
     * @param proglangId id ЯП в БД
     * @param userId id пользователя в БД
     * @return статистика пользователя по выбранному ЯП
     */
    public Statistics findByProglangIdAndUserId(long proglangId, long userId) {
        Statistics statistics = processSession(session -> session.createQuery(
                        "select s " +
                                "from Statistics s " +
                                "where s.proglang.id = :proglangId and s.user.id = :userId",
                        Statistics.class)
                .setParameter("proglangId", proglangId)
                .setParameter("userId", userId)
                .getSingleResult());
        return statistics;
    }
}
