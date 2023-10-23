package logic.handlers;

import data.User;
import data.question.Question;
import data.Storage;
/**
* Обработчик квиза
*/
public class QuizHandler {
    private Storage questions;

    public QuizHandler() {
        questions = new Storage();
    }

    /**
     * Обработчик квиза (сообщений в состоянии QUIZ)
     * @param message сообщение пользователя
     * @param currentUser пользователь который отправил сообщение
     * @return правильность ответа и следующий вопрос ИЛИ конец квиза
     */
    public String answerHandler(String message, User currentUser) {
        message = message.toLowerCase();

        int solvedCounter = currentUser.getCurrentQuestion();
        Question currentQuestion = null;
        String response;

        if (solvedCounter != -1) {
            currentQuestion = questions.getQuestionByIndex(solvedCounter);
        }

        if(message.equals("/stop")) {
            solvedCounter = -1;
            response =  "Тест завершен, чтобы начать заново введите /quiz";
        } else if (solvedCounter == -1) {
            solvedCounter = 0;
            currentQuestion = questions.getQuestionByIndex(solvedCounter);
            response = "Тест по ЯП JavaScript, состоит из " + questions.getSize() + " вопросов\n\n" + currentQuestion.questionText();

        } else if (solvedCounter < questions.getSize() - 1) {
            String checkResponse = checkCorrectness(message, currentQuestion);
            currentQuestion = questions.getQuestionByIndex(++solvedCounter);
            response =  checkResponse + currentQuestion.questionText();
        } else if (solvedCounter == questions.getSize() - 1) {
            currentUser.setCurrentQuestion(-1);
            String checkResponse = checkCorrectness(message, currentQuestion);
            response =  checkResponse + "Тест закончен!";
        } else {
            throw new RuntimeException("Error in quiz logic");
        }

        currentUser.setCurrentQuestion(solvedCounter);
        return  response;
    }

    /**
     * Проверка правильности ответа пользователя
     * @param answer ответ пользователя
     * @param currentQuestion текущий вопрос
     * @return текстовое описание, правильно ли ответил пользователь или нет
     */
    private String checkCorrectness(String answer, Question currentQuestion) {
        return answer.equals(currentQuestion.questionAnswer())
            ? "Вы ответили правильно!\n"
            : "Вы ответили неправильно! Правильный ответ:" + currentQuestion.questionAnswer() + '\n';
    }
}

