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
public class ProgquizDao {
    private final HibernateSessionFactoryUtil sessionFactoryUtil = new HibernateSessionFactoryUtil();
    public Progquiz findByProgquizId(Integer progquiz_id) {
        Session session = sessionFactoryUtil.getSessionFactory().openSession();
        Progquiz progquiz = null;
        try {
            progquiz = session.get(Progquiz.class, progquiz_id);
        } catch (final Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        } finally {
            session.close();
        }
        return progquiz;
    }

    public void save(Progquiz progquiz) {
        Session session = sessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        try {
            session.save(progquiz);
            tx1.commit();
        } catch (final Exception e) {
            tx1.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public void update(Progquiz progquiz) {
        Session session = sessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        try {
            session.update(progquiz);
            tx1.commit();
        } catch (final Exception e) {
            tx1.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public void delete(Progquiz progquiz) {
        Session session = sessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        try {
            session.remove(progquiz);
            tx1.commit();
        } catch (final Exception e) {
            tx1.rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public List<Progquiz> findAll() {
        Session session = sessionFactoryUtil.getSessionFactory().openSession();
        List<Progquiz> progquizs = new ArrayList<Progquiz>();
        try {
            progquizs = session.createQuery(
                            "from Progquiz")
                    .getResultList();
        } catch (final Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        } finally {
            session.close();
        }
        return progquizs;
    }
}
