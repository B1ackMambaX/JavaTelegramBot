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
public class handlersManager {
    private final UserService userService = new UserService();
    private final IdleHandler mainHandler;

    public handlersManager() {
        mainHandler = new IdleHandler();
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
                    QuizHandler quizHandler = new QuizHandler();
                    response = quizHandler.getResponse(message, currentUser);
                } else {
                    response = mainHandler.getResponse(message);
                }
                return response;
            case QUIZ:
                QuizHandler quizHandler = new QuizHandler();
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

    /**
     * Метод, который создает сообщения для клавиатуры в зависимости от состояния пользователя
     * @param currentUser пользователь
     * @return Сообщения для клавиатуры
     */
    public String[] keyboardTextInitializer(User currentUser) {
        State state = currentUser.getState();

        switch (state) {
            case IDLE:
                return new String[] {"/help", "/quiz"};
            case QUIZ:
                return new String[] {"/stop"};
            default:
                throw new RuntimeException("Error in keyboard text initializer");
        }
    }
}
