package database.utils;

import database.models.Quizstate;
import main.Config;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import database.models.Proglang;
import database.models.Progquiz;
import database.models.User;


/**
 * Фабрика сессий
 */
public class HibernateSessionFactoryUtil {
    private SessionFactory sessionFactory;
    private Config config = new Config();

    public HibernateSessionFactoryUtil() {}

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://" + config.getDatabaseHost() + ":" + config.getDatabasePort() + "/" + config.getDatabaseName());
                configuration.setProperty("hibernate.connection.username", config.getDatabaseUser());
                configuration.setProperty("hibernate.connection.password", config.getDatabasePass());
                configuration.addAnnotatedClass(Proglang.class);
                configuration.addAnnotatedClass(Progquiz.class);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Quizstate.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                System.out.println("Exception:" + e);
            }
        }
        return sessionFactory;
    }
}
