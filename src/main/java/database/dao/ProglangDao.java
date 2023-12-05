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
     * Найти вопросы по языку программирования
     * @param proglang_id id ЯП
     * @param offset отступ
     * @param limit сколько вопросов брать
     * @return лист вопросов
     */
    public List<Progquiz> findProgquizByProglangId(Integer proglang_id, Integer offset, Integer limit) {
        List<Progquiz> progquizzes = new ArrayList<Progquiz>();
        progquizzes.addAll(processSession(session -> session.createQuery(
                        "select p " +
                                "from Progquiz p " +
                                "where p.proglang.id = :proglang_id",
                        Progquiz.class)
                .setParameter("proglang_id", proglang_id)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList()));

        return progquizzes;
    }
    /**
     * Подсчитать количество вопросов по языку программирования
     * @param proglang_id id ЯП
     * @return количество вопросов
     */
    public Long countProgquizzesByProglangId(Integer proglang_id) {
        Long count = processSession(session -> session.createQuery(
                        "select count(p) " +
                                "from Progquiz p " +
                                "where p.proglang.id = :proglang_id",
                        Long.class)
                .setParameter("proglang_id", proglang_id)
                .getSingleResult());

        return count;
    }
}
