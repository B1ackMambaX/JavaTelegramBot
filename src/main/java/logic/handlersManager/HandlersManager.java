package logic.handlersManager;

import database.models.User;
import database.services.UserService;
import logic.Response;
import logic.handlers.IdleHandler;
import logic.handlers.QuizHandler;
import database.models.types.State;

/**
 * Класс, который реализует выбор обработчика сообщения в зависимости от состояния пользователя
 */
public class HandlersManager {
    private final UserService userService;
    private final IdleHandler idleHandler;
    private final  QuizHandler quizHandler;

    public HandlersManager() {
        idleHandler = new IdleHandler();
        quizHandler = new QuizHandler();
        userService = new UserService();
    }

    /**
     * Конструктор для тестов
     * @param idleHandler мок обработчика состояния IDLE
     * @param quizHandler мок обработчика состояния QUIZ
     * @param userService мок сервиса пользователя
     */
    public HandlersManager(IdleHandler idleHandler, QuizHandler quizHandler, UserService userService) {
        this.idleHandler = idleHandler;
        this.quizHandler = quizHandler;
        this.userService = userService;
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
                if (message.equals("/quiz")) {
                    currentUser.setState(State.QUIZ);
                    userService.update(currentUser);
                    response = quizHandler.getResponse(message, currentUser);
                } else {
                    response = idleHandler.getResponse(message);
                }
                return response;
            case QUIZ:
                if (message.equals("/stop")) {
                    currentUser.setState(State.IDLE);
                    userService.update(currentUser);
                    response = quizHandler.getResponse(message, currentUser);
                } else {
                    response = quizHandler.getResponse(message, currentUser);
                    if (response.message().contains("Тест закончен!")) {
                        currentUser.setState(State.IDLE);
                        userService.update(currentUser);
                    }
                }
                return response;
            default:
                throw new RuntimeException("Error in state manager");
        }
    }
}
