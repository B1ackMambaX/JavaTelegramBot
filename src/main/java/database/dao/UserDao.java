package database.dao;

import database.models.User;
import database.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDao {
    public User findByUserId(Integer user_id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        User user = session.get(User.class, user_id);
        session.close();
        return user;
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
