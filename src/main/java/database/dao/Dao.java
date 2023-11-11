package database.dao;

import org.hibernate.Session;
import database.utils.HibernateSessionFactoryUtil;
import org.hibernate.Transaction;

import java.util.function.Function;

public abstract class Dao {
    public  <T> T processSession(Function<Session, T> function) {
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

    public <T> T processTransaction(Function<Session, T> function) {
        final HibernateSessionFactoryUtil sessionFactoryUtil = new HibernateSessionFactoryUtil();
        final Session session = sessionFactoryUtil.getSessionFactory().openSession();
        final Transaction transaction = session.beginTransaction();

        try {
            function.apply(session);
            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        return null;
    }
}


