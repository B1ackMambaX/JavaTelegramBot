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
        List<Statistics> statistics = new ArrayList<Statistics>();
        statistics.addAll(
                processSession(session -> session.createQuery(
                                "select s " +
                                        "from Statistics s " +
                                        "where s.proglang.id = :proglang_id " +
                                        "order by s.score desc",
                                Statistics.class)
                        .setParameter("proglang_id", proglangId)
                        .setMaxResults(10) // добавляем лимит в 10 сущностей
                        .getResultList()));
        return statistics;
    }

    /**
     * Найти всю статистику по пользователю по его id в БД
     * @param userId id ЯП в БД
     * @return статистики
     */
    public List<Statistics> findAllByUserId(long userId) {
        List<Statistics> statistics = new ArrayList<Statistics>();
        statistics.addAll(
                processSession(session -> session.createQuery(
                                "select s " +
                                        "from Statistics s " +
                                        "where s.user.id = :user_id",
                                Statistics.class)
                        .setParameter("user_id", userId)
                        .getResultList()));
        return statistics;
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
                                "where s.proglang.id = :proglang_id and s.user.id = :user_id",
                        Statistics.class)
                .setParameter("proglang_id", proglangId)
                .setParameter("user_id", userId)
                .getSingleResult());
        return statistics;
    }
}
