package logic.stateManager;

import database.models.User;
import database.services.UserService;
import logic.handlers.TextHandler;
import logic.handlers.QuizHandler;
import database.models.types.State;

/**
 * Машина состояний
 */
public class StateManager {
    private final UserService userService = new UserService();
    private final TextHandler mainHandler;

    public StateManager() {
        mainHandler = new TextHandler();
    }

    /**
     * Метод, который выбирает обработчик сообщения в зависимости от состояния пользователя
     * @param message сообщение
     * @param currentUser пользователь
     * @return Ответ на сообщение
     */
    public String chooseHandler(String message, User currentUser) {
        String response;
        State state = currentUser.getState();
        switch (state) {
            case IDLE:
                if (message.equals("/quiz")) {
                    currentUser.setState(State.QUIZ);
                    userService.updateUser(currentUser);
                    QuizHandler quizHandler = new QuizHandler(1);
                    response = quizHandler.answerHandler(message, currentUser);
                } else {
                    response = mainHandler.messageHandler(message);
                }
                return response;
            case QUIZ:
                QuizHandler quizHandler = new QuizHandler(1);
                if (message.equals("/stop")) {
                    currentUser.setState(State.IDLE);
                    userService.updateUser(currentUser);
                    response = quizHandler.answerHandler(message, currentUser);
                } else {
                    response = quizHandler.answerHandler(message, currentUser);
                    if (response.contains("Тест закончен!")) {
                        currentUser.setState(State.IDLE);
                        userService.updateUser(currentUser);
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
