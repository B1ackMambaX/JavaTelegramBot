package database.dao;

import database.models.Progquiz;
import database.models.Progquiz;
import database.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ProgquizDao {
    public Progquiz findByProgquizId(Integer progquiz_id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Progquiz progquiz = session.get(Progquiz.class, progquiz_id);
        session.close();
        return progquiz;
    }

    public void save(Progquiz progquiz) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(progquiz);
        tx1.commit();
        session.close();
    }

    public void update(Progquiz progquiz) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(progquiz);
        tx1.commit();
        session.close();
    }

    public void delete(Progquiz progquiz) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(progquiz);
        tx1.commit();
        session.close();
    }

    public List<Progquiz> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Progquiz> progquizs = session.createQuery(
                "from Progquiz")
                .getResultList();
        session.close();
        return progquizs;
    }
}
