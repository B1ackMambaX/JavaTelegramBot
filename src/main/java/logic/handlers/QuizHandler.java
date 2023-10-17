package logic.handlers;

import data.question.Question;
import data.Storage;
/**
* Обработчик квиза
*/
public class QuizHandler {
    private Storage questions;
    private int solvedCounter;
    private Question currentQuestion;

    public QuizHandler() {
        questions = new Storage();
        solvedCounter = -1;
    }

    /**
     * Обработчик квиза
     * @param message сообщение пользователя
     * @return правильность ответа и следующий вопрос ИЛИ конец квиза
     */
    public String answerHandler(String message) {
        message = message.toLowerCase();

        if (solvedCounter == -1) {
            solvedCounter = 0;
            questions = new Storage();
            currentQuestion = questions.getQuestionByIndex(solvedCounter);
            return "Тест по ЯП JavaScript, состоит из " + questions.getSize() + " вопросов\n\n" + currentQuestion.questionText();
        } else if (solvedCounter < questions.getSize() - 1) {
            String checkResponse = checkCorrectness(message);
            currentQuestion = questions.getQuestionByIndex(++solvedCounter);
            return checkResponse + currentQuestion.questionText();
        } else if (solvedCounter == questions.getSize() - 1) {
            solvedCounter = 0;
            String checkResponse = checkCorrectness(message);
            return checkResponse + "Тест закончен!";
        } else {
            throw new RuntimeException("Error in quiz logic");
        }
    }

    /**
     * Проверка правильности ответа пользователя
     * @param answer ответ пользователя
     * @return текстовое описание, правильно ли ответил пользователь или нет
     */
    private String checkCorrectness(String answer) {
        return answer.equals(currentQuestion.questionAnswer())
            ? "Вы ответили правильно!\n"
            : "Вы ответили неправильно! Правильный ответ:" + currentQuestion.questionAnswer() + '\n';
    }
}

