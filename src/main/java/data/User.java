package data;
import logic.stateManager.State;

import java.util.Objects;


/**
 * Класс пользователя
 */
public class User {
    private final long id;
    private State userState;
    private int currentQuestion;

    public User(long id) {
        this.id = id;
        userState = State.IDLE;
        currentQuestion = -1;
    }
    public User(long id, State userState, int currentQuestion) {
        this.id = id;
        this.userState = userState;
        this.currentQuestion = currentQuestion;
    }

    public void setUserState(State newState) {
        userState = newState;
    }

    public State getUserState() {
        return userState;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int newQuestion) {
        currentQuestion = newQuestion;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userState);
    }
}
