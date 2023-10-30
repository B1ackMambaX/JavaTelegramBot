package database.dao;

import database.models.Proglang;
import database.models.Progquiz;
import org.hibernate.Session;
import org.hibernate.Transaction;
import database.utils.HibernateSessionFactoryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Data access object для таблицы с языками программирования
 */
public class ProglangDao {
    public Proglang findByProglangId(Integer proglang_id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Proglang proglang = session.get(Proglang.class, proglang_id);
        session.close();
        return proglang;
    }
    public void save(Proglang proglang) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(proglang);
        tx1.commit();
        session.close();
    }

    public void update(Proglang proglang) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(proglang);
        tx1.commit();
        session.close();
    }

    public void delete(Proglang proglang) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.remove(proglang);
        tx1.commit();
        session.close();
    }

    /**
     * Найти все вопросы по языку программирования
     * @param proglang_id id ЯП
     * @return лист вопросов
     */
    public List<Progquiz> findProgquizzesByProglangId(Integer proglang_id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Progquiz> progquizzes = new ArrayList<Progquiz>();
        try {
            progquizzes = session.createQuery(
                            "select p " +
                                    "from Progquiz p " +
                                    "where p.proglang.proglang_id = :proglang_id",
                            Progquiz.class)
                    .setParameter("proglang_id", proglang_id)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Zero length!: " + e);
            e.printStackTrace();
        } finally {
            session.close();
        }

        return progquizzes;
    }

    public List<Proglang> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Proglang> proglangs = session.createQuery(
                "from Proglang")
                .getResultList();
        session.close();
        return proglangs;
    }
}
