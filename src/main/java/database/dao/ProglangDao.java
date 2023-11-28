package database.dao;

import database.models.Proglang;
import database.models.Progquiz;

import java.util.ArrayList;
import java.util.List;

/**
 * Data access object для таблицы с языками программирования
 */
public class ProglangDao extends BaseDao<Proglang> {
    public ProglangDao() {
        super(Proglang.class);
    }

    /**
     * Найти язык программирования по его id в БД
     * @param proglang_id id ЯП в БД
     * @return язык программирования
     */
    public Proglang findByProglangId(Integer proglang_id) {
        return processSession(session ->
                session.get(Proglang.class, proglang_id));
    }

    /**
     * Найти все вопросы по языку программирования
     * @param proglang_id id ЯП
     * @return лист вопросов
     */
    public List<Progquiz> findProgquizzesByProglangId(Integer proglang_id) {
        List<Progquiz> progquizzes = new ArrayList<Progquiz>();
        progquizzes = processSession(session -> session.createQuery(
                     "select p " +
                             "from Progquiz p " +
                             "where p.proglang.id = :proglang_id",
                     Progquiz.class)
                         .setParameter("proglang_id", proglang_id)
                         .getResultList());

        return progquizzes;
    }

    /**
     * Подсчитать количество вопросов по языку программирования
     * @param proglang_id id ЯП
     * @return количество вопросов
     */
    public Integer countProgquizzesByProglangId(Integer proglang_id)
    {
        Integer count = processSession(session -> session.createQuery(
                        "select count(p) " +
                                "from Progquiz p " +
                                "where p.proglang.id = :proglang_id",
                        Integer.class)
                .setParameter("proglang_id", proglang_id)
                .getSingleResult());

        return count;
    }
}
