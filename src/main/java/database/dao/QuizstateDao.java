package database.dao;


import database.models.User;
import database.models.Quizstate;

public class QuizstateDao extends BaseDao<Quizstate> {
    public QuizstateDao() {
        super(Quizstate.class);
    }

    /**
     * Найти состояние по user id в БД
     * @param userId userId в БД
     * @return Сущность пользователя
     */
    public Quizstate getByUserId(long userId) {
        return processSession(session ->
                session.createQuery(
                        "from Quizstate q where q.user.id = :userId", Quizstate.class)
                .setParameter("userId", userId)
                .getSingleResult()
        );
    }
}
