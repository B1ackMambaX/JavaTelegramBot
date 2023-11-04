package database.services;

import database.dao.UserDao;
import database.models.User;
import database.models.types.Plathform;

import java.util.List;

/**
 * Слой сервиса который предоставляет CRUD операции для таблицы польщователей
 */
public class UserService {
    private UserDao userDao = new UserDao();

    public UserService() {
    }

    public User find(Integer user_id){
        return userDao.findByUserId(user_id);
    }

    public void save(User user) {
        userDao.save(user);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public List<User> findAllByPlathform(Plathform plathform) {
        return userDao.findByPlathform(plathform);
    }

    public User findUserByPlathformAndId(Plathform plathform, Long plathform_id) {
        return userDao.findOneByPlathformAndId(plathform, plathform_id);
    }
}
