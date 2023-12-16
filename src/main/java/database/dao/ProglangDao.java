package database.dao;

import database.models.Proglang;
import database.models.Progquiz;

import javax.persistence.NoResultException;
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
     * @param proglangId id ЯП в БД
     * @return язык программирования
     */
    public Proglang findByProglangId(long proglangId) {
        return processSession(session ->
                session.get(Proglang.class, proglangId));
    }

    /**
     * Получение ID ЯП по имени
     * @param proglangName имя языка программирования
     * @return ID языка программирования
     */
    public long getIdByName(String proglangName) {
        try {
            return processSession(session -> session.createQuery(
                            "select p.id " +
                                    "from Proglang p " +
                                    "where lower(p.proglangName) = :proglangName",
                            Long.class)
                    .setParameter("proglangName", proglangName)
                    .getSingleResult());
        } catch (NoResultException e) {
            return -1;
        }
    }

    /**
     * Получение имен всех ЯП в БД
     * @return список имен ЯП
     */
    public List<String> getAllNames() {
        return new ArrayList<>(processSession(session -> session.createQuery(
                        "select p.proglangName " +
                                "from Proglang p ",
                        String.class)
                .getResultList()));
    }

    /**
     * Найти вопросы по языку программирования
     * @param proglangId id ЯП
     * @param offset отступ
     * @return лист вопросов
     */
    public Progquiz findProgquizByProglangId(long proglangId, int offset) {
        return processSession(session -> session.createQuery(
                        "select p " +
                                "from Progquiz p " +
                                "where p.proglang.id = :proglangId",
                        Progquiz.class)
                .setParameter("proglangId", proglangId)
                .setFirstResult(offset)
                .setMaxResults(1)
                .getSingleResult());
    }
    /**
     * Подсчитать количество вопросов по языку программирования
     * @param proglangId id ЯП
     * @return количество вопросов
     */
    public long countProgquizzesByProglangId(long proglangId) {
        long count = processSession(session -> session.createQuery(
                        "select count(p) " +
                                "from Progquiz p " +
                                "where p.proglang.id = :proglangId",
                        Long.class)
                .setParameter("proglangId", proglangId)
                .getSingleResult());

        return count;
    }
}
