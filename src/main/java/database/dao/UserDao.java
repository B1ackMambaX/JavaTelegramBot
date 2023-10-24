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
public class UserDao {
    public User findByUserId(Integer user_id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        User user = session.get(User.class, user_id);
        session.close();
        return user;
    }

    /**
     * @param plathform платформа на которой зарегистрирован пользователь
     * @param plathform_id id пользователя на этой плафторме
     * @return нужный пользователь
     */
    public User findOneByPlathformAndId(Plathform plathform, Long plathform_id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        User user = null;
        try {
            user = session.createQuery(
                            "select u " +
                                    "from User u " +
                                    "where u.plathform = :plathform and u.plathform_id = :plathform_id",
                            User.class)
                    .setParameter("plathform", plathform)
                    .setParameter("plathform_id", plathform_id)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("Don't find this user: " + e);
            e.printStackTrace();
        }
        session.close();
        return user;
    }

    public List<User> findByPlathform(Plathform plathform) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<User> users = new ArrayList<User>();
        try {
            users = session.createQuery(
                            "select User " +
                                    "from User " +
                                    "where User.plathform = :plathform",
                            User.class)
                    .setParameter("plathform", plathform)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Zero length users by plathform!: " + e);
            e.printStackTrace();
        }
        session.close();
        return users;
    }

    public void save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    public void delete(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }

    public List<User> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<User> users = session.createQuery(
                        "from User")
                .getResultList();
        session.close();
        return users;
    }
}
