package database.dao;

import org.hibernate.Session;
import database.utils.HibernateSessionFactoryUtil;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Function;

/**
 * Базовый класс DAO который реализует основные операции и от которого наследуются конкретные реализации DAO
 * @param <DAOEntity> {@link database.models модель} с которой работает конкретная реализация DAO
 */
public abstract class BaseDao<DAOEntity> {
    private final Class<DAOEntity> entityClass;

    /**
     * @param entityClass модель с которой работает DAO
     */
    public BaseDao(Class<DAOEntity> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Функция выполняющая работу с сессией
     * @return результат работы с сессией
     */
    protected  <T> T processSession(Function<Session, T> function) {
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

    /**
     * Функция работающая с транзакцией
     * @return результат работы с транзакцией
     */
    protected  <R> R processTransction(Function<Session, R> function) {
        HibernateSessionFactoryUtil sessionFactoryUtil = new HibernateSessionFactoryUtil();

        Session session = sessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            R result = function.apply(session);
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

    /**
     * Поиск всех записей в БД по конкретной {@link database.models модели}
     * @return лист экземпляров классов {@link database.models моделей}
     */
    public List<DAOEntity> findAll() {
        return processSession(session -> session.createQuery("from " + entityClass.getName(), entityClass).list());
    }

    /**
     * Удаление записи из БД
     * @param entity экземпляр класса {@link database.models модели}
     */
    public void delete(DAOEntity entity) {
        processTransction((session) -> {
            session.delete(entity);
            return null;
        });
    }

    /**
     * Сохранение записи в БД
     * @param entity экземпляр класса {@link database.models модели}
     */
    public void save(DAOEntity entity) {
        processTransction((session) -> {
            session.save(entity);
            return null;
        });
    }

    /**
     * Обновление записи в БД
     * @param entity экземпляр класса {@link database.models модели}
     */
    public void update(DAOEntity entity) {
        processTransction((session) -> {
            session.update(entity);
            return null;
        });
    }
}


