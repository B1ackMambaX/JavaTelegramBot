package database.services;

import database.dao.UserDao;
import database.models.User;
import database.models.types.Plathform;

import java.util.List;

public class UserService {
    private UserDao userDao = new UserDao();

    public UserService() {
    }

    public User findUser(Integer user_id){
        return userDao.findByUserId(user_id);
    }

    public void saveUser(User user) {
        userDao.save(user);
    }

    public void deleteUser(User user) {
        userDao.delete(user);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    public List<User> findAllUsersByPlathform(Plathform plathform) {
        return userDao.findByPlathform(plathform);
    }

    public User findUserByPlathformAndId(Plathform plathform, Long plathform_id) {
        return userDao.findOneByPlathformAndId(plathform, plathform_id);
    }
}
