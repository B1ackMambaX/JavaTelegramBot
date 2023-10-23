package database.dao;

import database.models.Proglang;
import database.models.Progquiz;
import org.hibernate.Session;
import org.hibernate.Transaction;
import database.utils.HibernateSessionFactoryUtil;
import java.util.List;

public class ProglangDao {
    public Proglang findByProglangId(Integer proglang_id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Proglang.class, proglang_id);
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
        session.delete(proglang);
        tx1.commit();
        session.close();
    }

    public Progquiz findProgquizByProglangId(Integer proglang_id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Progquiz.class, proglang_id);
    }

    public List<Proglang> findAll() {
        List<Proglang> proglangs = (List<Proglang>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("from Proglang").list();
        return proglangs;
    }
}
