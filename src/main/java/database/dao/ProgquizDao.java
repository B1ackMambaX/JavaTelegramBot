package database.dao;

import database.models.Progquiz;
import database.models.Progquiz;
import database.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Data access object для таблицы с вопросами
 */
public class ProgquizDao extends BaseDao<Progquiz> {

    public ProgquizDao() {
        super(Progquiz.class);
    }

    /**
     * Нахождение вопроса по его id в БД
     * @param progquiz_id id вопроса в БД
     * @return вопрос
     */
    public Progquiz findByProgquizId(Integer progquiz_id) {
        return processSession(session -> session.get(Progquiz.class, progquiz_id));
    }
}
