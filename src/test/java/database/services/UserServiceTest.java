package database.services;

import database.dao.QuizstateDao;
import database.dao.UserDao;
import database.models.Quizstate;
import database.models.User;
import database.models.types.Plathform;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserServiceTest {
    private QuizstateDao quizstateDao;
    private UserDao userDao;
    private UserService userService;
    @BeforeEach
    void setUp() {
        quizstateDao = Mockito.mock(QuizstateDao.class);
        userDao = Mockito.mock(UserDao.class);
        userService = new UserService(userDao, quizstateDao);
    }

    /**
     * Тест на логин уже существующего пользователя
     */
    @Test
    void testExistingUser() {
        User testUser = new User(Plathform.TG, 52L);
        Mockito.when(userDao.findOneByPlathformAndId(Plathform.TG, 52L)).thenReturn(testUser);
        Mockito.doNothing().when(quizstateDao).save(new Quizstate(testUser));

        User existingUser = userService.login(Plathform.TG, 52L);

        Assertions.assertEquals(existingUser, testUser);
    }

    /**
     * Тест на регистрацию нового пользователя
     */
    @Test
    void testNewUser() {
        User testUser = new User(Plathform.TG, 52L);
        Mockito.when(userDao.findOneByPlathformAndId(Plathform.TG, 52L)).thenReturn(null);
        Mockito.doNothing().when(quizstateDao).save(new Quizstate(testUser));

        User newUser = userService.login(Plathform.TG, 52L);

        Assertions.assertEquals(newUser, testUser, "Проверка добавления нового пользователя");
    }
}
