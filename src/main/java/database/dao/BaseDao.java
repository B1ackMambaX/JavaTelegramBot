package database.dao;

import org.hibernate.Session;
import database.utils.HibernateSessionFactoryUtil;
import org.hibernate.Transaction;

import java.util.function.Function;

public abstract class BaseDao {
    private final Class<T> entityClass;

    public BaseDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public <T> T processSession(Function<Session, T> function) {
        HibernateSessionFactoryUtil sessionFactoryUtil = new HibernateSessionFactoryUtil();

        Session session = sessionFactoryUtil.getSessionFactory().openSession();
        try {
            return function.apply(session);
        } catch (final Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public <R> R inTransaction(Function<Transaction, R> function) {
        HibernateSessionFactoryUtil sessionFactoryUtil = new HibernateSessionFactoryUtil();

        Session session = sessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            R result = function.apply(transaction);
            transaction.commit();
            return result;
        } catch (final Exception e) {
            transaction.rollback();
            System.out.println("Error: " + e);
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public List<T> findAll() {
        return processSession(session -> session.createQuery("from " + entityClass.getName(), entityClass).list());
    }

    public void delete(Class<T> entity) {
        inTransaction(() -> {
            final Session session = sessionFactory.getCurrentSession();
            session.delete(entity);
            return null;
        });
    }

    public void save(Class<T> entity) {
        inTransaction(() -> {
            final Session session = sessionFactory.getCurrentSession();
            session.save(entity);
            return null;
        });
    }

    public void update(Class<T> entity) {
        inTransaction(() -> {
            final Session session = sessionFactory.getCurrentSession();
            session.update(entity);
            return null;
        });
    }
}


