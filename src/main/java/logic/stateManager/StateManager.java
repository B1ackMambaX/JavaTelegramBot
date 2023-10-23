package logic.stateManager;

import data.User;
import logic.handlers.MainHandler;
import logic.handlers.QuizHandler;

/**
 * Машина состояний
 */
public class StateManager {
    private final MainHandler mainHandler;
    private QuizHandler quizHandler;

    public StateManager() {
        mainHandler = new MainHandler();
    }

    /**
     * Метод, который выбирает обработчик сообщения в зависимости от состояния пользователя
     * @param message сообщение
     * @param currentUser пользователь
     * @return Ответ на сообщение
     */
    public String chooseHandler(String message, User currentUser) {
        String response;
        State state = currentUser.getUserState();
        switch (state) {
            case IDLE:
                if (message.equals("/quiz")) {
                    currentUser.setUserState(State.QUIZ);
                    quizHandler = new QuizHandler();
                    response = quizHandler.answerHandler(message, currentUser);
                } else {
                    response = mainHandler.messageHandler(message);
                }
                return response;
            case QUIZ:
                if (message.equals("/stop")) {
                    currentUser.setUserState(State.IDLE);
                    response = quizHandler.answerHandler(message, currentUser);
                } else {
                    response = quizHandler.answerHandler(message, currentUser);
                    if (response.contains("Тест закончен!")) {
                        currentUser.setUserState(State.IDLE);
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
        State state = currentUser.getUserState();

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
