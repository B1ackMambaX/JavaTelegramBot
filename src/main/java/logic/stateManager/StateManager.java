package logic.stateManager;

import logic.handlers.MainHandler;
import logic.handlers.QuizHandler;

public class StateManager {
    private State state;
    private final MainHandler mainHandler;
    private QuizHandler quizHandler;

    public StateManager() {
        state = State.IDLE;
        mainHandler = new MainHandler();
    }

    public State getState() {
        return state;
    }

    private void setState(State state) {
        this.state = state;
    }

    public String chooseHandler(String message) {
        String response;
        switch (state) {
            case IDLE:
                if (message.equals("/quiz")) {
                    setState(State.QUIZ);
                    quizHandler = new QuizHandler();
                    response = quizHandler.answerHandler(message);
                } else {
                    response = mainHandler.messageHandler(message);
                }
                return response;
            case QUIZ:
                if (message.equals("/stop")) {
                    setState(State.IDLE);
                    response = mainHandler.messageHandler(message);
                } else {
                    response = quizHandler.answerHandler(message);
                    if (response.contains("Тест закончен!")) {
                        setState(State.IDLE);
                    }
                }
                return response;
            default:
                throw new RuntimeException("Error in state manager");
        }
    }
}
