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
    private final HibernateSessionFactoryUtil sessionFactoryUtil = new HibernateSessionFactoryUtil();
    public User findByUserId(Integer user_id) {
        Session session = sessionFactoryUtil.getSessionFactory().openSession();
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
        Session session = sessionFactoryUtil.getSessionFactory().openSession();
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
        } finally {
            session.close();
        }
        return user;
    }

    public List<User> findByPlathform(Plathform plathform) {
        Session session = sessionFactoryUtil.getSessionFactory().openSession();
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
        } finally {
            session.close();
        }
        return users;
    }

    public void save(User user) {
        Session session = sessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        try {
            session.save(user);
            tx1.commit();
        } catch (final Exception e) {
            tx1.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public void update(User user) {
        Session session = sessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        try {
            session.update(user);
            tx1.commit();
        } catch (final Exception e) {
            tx1.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public void delete(User user) {
        Session session = sessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        try {
            session.remove(user);
            tx1.commit();
        } catch (final Exception e) {
            tx1.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public List<User> findAll() {
        Session session = sessionFactoryUtil.getSessionFactory().openSession();
        List<User> users = new ArrayList<User>();
        try {
            users = session.createQuery(
                            "from User")
                    .getResultList();
        } catch (final Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        } finally {
            session.close();
        }
        return users;
    }
}
