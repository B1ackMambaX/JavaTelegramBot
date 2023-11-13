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

    public void update(User user) {
        userDao.update(user);
    }
    /**
     * Добавление нового пользователя либо получение уже существующего
     * @param plathformId id пользователя на платформе
     * @param plathform платформа с которой пользователь использует бота
     * @return пользователь
     */
    public User login(Plathform plathform, long plathformId) {
        User currentUser = userDao.findOneByPlathformAndId(plathform, plathformId);
        if (currentUser == null) {
            currentUser = new User(plathform, plathformId);
            userDao.save(currentUser);
        }
        return currentUser;
    }
}
