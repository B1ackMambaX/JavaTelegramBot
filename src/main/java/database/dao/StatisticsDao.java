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
     * @param proglang_id id ЯП в БД
     * @return статистики
     */
    public List<Statistics> findAllByProglangId(Integer proglang_id) {
        List<Statistics> statistics = new ArrayList<Statistics>();
        statistics.addAll(
                processSession(session -> session.createQuery(
                                "select s " +
                                        "from Statistics s " +
                                    "where s.proglang.id = :proglang_id",
                                Statistics.class)
                        .setParameter("proglang_id", proglang_id)
                        .getResultList()));
        return statistics;
    }

    /**
     * Найти всю статистику по пользователю по его id в БД
     * @param user_id id ЯП в БД
     * @return статистики
     */
    public List<Statistics> findAllByUserId(Integer user_id) {
        List<Statistics> statistics = new ArrayList<Statistics>();
        statistics.addAll(
                processSession(session -> session.createQuery(
                                "select s " +
                                        "from Statistics s " +
                                        "where s.user.id = :user_id",
                                Statistics.class)
                        .setParameter("user_id", user_id)
                        .getResultList()));
        return statistics;
    }

    /**
     * Получение статистики пользователя по одному из ЯП
     * @param proglang_id id ЯП в БД
     * @param user_id id пользователя в БД
     * @return статистика пользователя по выбранному ЯП
     */
    public Statistics findByProglangIdAndUserId(Integer proglang_id, Integer user_id) {
        Statistics statistics = processSession(session -> session.createQuery(
                        "select s " +
                                "from Statistics s " +
                                "where s.proglang.id = :proglang_id and s.user.id = :user_id",
                        Statistics.class)
                .setParameter("proglang_id", proglang_id)
                .setParameter("user_id", user_id)
                .getSingleResult());
        return statistics;
    }
}
