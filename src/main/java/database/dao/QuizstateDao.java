package database.dao;


import database.models.User;
import database.models.Quizstate;

public class QuizstateDao extends BaseDao<Quizstate> {
    public QuizstateDao() {
        super(Quizstate.class);
    }

    /**
     * Найти состояние по user id в БД
     * @param user_id user_id в БД
     * @return Сущность пользователя
     */
    public Quizstate getByUserId(Integer user_id) {
        return processSession(session ->
                session.createQuery(
                        "from Quizstate q where q.user.id = :user_id", Quizstate.class)
                .setParameter("user_id", user_id)
                .getSingleResult()
        );
    }
}
