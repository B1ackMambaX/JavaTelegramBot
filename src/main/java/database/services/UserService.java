package database.services;

import database.dao.QuizstateDao;
import database.dao.UserDao;
import database.models.Quizstate;
import database.models.User;
import database.models.types.Plathform;

import java.util.List;

/**
 * Слой сервиса который предоставляет CRUD операции для таблицы польщователей
 */
public class UserService {
    private final UserDao userDao = new UserDao();
    private final QuizstateDao quizstateDao = new QuizstateDao();

    public UserService() {
    }

    /**
     * Обновление сущности User
     * @param user Обновляемый пользователь
     */
    public void update(User user) {
        userDao.update(user);
    }

    /**
     * Получение Quizstate по user_id в БД
     * @param user_id user_id в БД
     * @return Quizstate
     */
    public Quizstate getQuizState(long user_id) {
        return quizstateDao.getByUserId(user_id);
    }

    /**
     * Обновление состояния
     * @param quizstate Состояние, которое необходимо обновить
     */
    public void updateQuizState(Quizstate quizstate) {
        quizstateDao.update(quizstate);
    }


    /**
     * Добавление нового пользователя либо получение уже существующего.
     * Также добавления состояния/ий при создании нового пользователя.
     * @param plathformId id пользователя на платформе
     * @param plathform платформа с которой пользователь использует бота
     * @return пользователь
     */
    public User login(Plathform plathform, long plathformId, String username) {
        User currentUser = userDao.findOneByPlathformAndId(plathform, plathformId);
        if (currentUser == null) {
            currentUser = new User(plathform, plathformId, username);
            userDao.save(currentUser);

            Quizstate currentQuizStateUser = new Quizstate(currentUser);
            quizstateDao.save(currentQuizStateUser);
        }
        return currentUser;
    }
}
