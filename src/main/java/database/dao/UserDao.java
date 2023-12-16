package database.dao;

import database.models.User;
import database.models.types.Plathform;
import database.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Data access object для таблицы пользователей
 */
public class UserDao extends BaseDao<User> {
    public UserDao() {
        super(User.class);
    }

    /**
     * Нахождение пользователя по его id в БД
     * @param userId id пользователя в БД
     * @return нужный пользователь
     */
    public User findByUserId(long userId) {
        return processSession(session -> session.get(User.class, userId));
    }

    /**
     * @param plathform платформа на которой зарегистрирован пользователь
     * @param plathformId id пользователя на этой плафторме
     * @return нужный пользователь
     */
    public User findOneByPlathformAndId(Plathform plathform, long plathformId) {
        return processSession(session -> session.createQuery(
                        "select u " +
                                "from User u " +
                                "where u.plathform = :plathform and u.plathform_id = :plathform_id",
                        User.class)
                            .setParameter("plathform", plathform)
                            .setParameter("plathform_id", plathformId)
                            .getSingleResult());
    }

    /**
     * Нахождение всех пользователей по платформе
     * @param plathform платформа
     * @return список пользователей на платформе
     */
    public List<User> findByPlathform(Plathform plathform) {
        return  processSession(session -> session.createQuery(
                        "select User " +
                                "from User " +
                                "where User.plathform = :plathform",
                        User.class)
                            .setParameter("plathform", plathform)
                            .getResultList());
    }
}
