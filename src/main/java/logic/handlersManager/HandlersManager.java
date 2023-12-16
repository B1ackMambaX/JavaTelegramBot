package logic.handlersManager;

import database.models.User;
import logic.Response;
import logic.handlers.IdleHandler;
import logic.handlers.QuizHandler;
import database.models.types.State;
import logic.handlers.StatisticsHandler;

/**
 * Класс, который реализует выбор обработчика сообщения в зависимости от состояния пользователя
 */
public class HandlersManager {
    private final IdleHandler idleHandler;
    private final  QuizHandler quizHandler;
    private final StatisticsHandler statisticsHandler;

    public HandlersManager() {
        idleHandler = new IdleHandler();
        quizHandler = new QuizHandler();
        statisticsHandler = new StatisticsHandler();
    }

    /**
     * Конструктор для тестов
     * @param idleHandler мок обработчика состояния IDLE
     * @param quizHandler мок обработчика состояния QUIZ
     */
    public HandlersManager(IdleHandler idleHandler, QuizHandler quizHandler, StatisticsHandler statisticsHandler) {
        this.idleHandler = idleHandler;
        this.quizHandler = quizHandler;
        this.statisticsHandler = statisticsHandler;
    }

    /**
     * Метод, который выбирает обработчик сообщения в зависимости
     * от состояния пользователя, передает обработчику сообщение и возвращает ответ
     * @param message сообщение
     * @param currentUser пользователь
     * @return Ответ на сообщение
     */
    public Response getResponseFromHandler(String message, User currentUser) {
        Response response;
        State state = currentUser.getState();
        switch (state) {
            case IDLE:
                response = switch (message) {
                    case "/quiz" -> quizHandler.getResponse(message, currentUser);
                    case "/mystats" -> statisticsHandler.getUserStatistic(currentUser);
                    case "/leaderboard" -> statisticsHandler.getLeaderboard(currentUser, message);
                    default -> idleHandler.getResponse(message);
                };
                return response;
            case QUIZ:
                    return quizHandler.getResponse(message, currentUser);
            case LEADERBOARD:
                return statisticsHandler.getLeaderboard(currentUser, message);
            default:
                throw new RuntimeException("Error in state manager");
        }
    }
}
