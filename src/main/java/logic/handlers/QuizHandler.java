package logic.handlers;

import database.models.User;
import database.models.Progquiz;
import database.services.ProglangService;
import database.services.UserService;

import java.util.ArrayList;
import java.util.List;

/**
* Обработчик квиза
*/
public class QuizHandler {
    private final ProglangService proglangService;
    private final UserService userService;

    /**
     * @param proglang_id id языка программирования по которому проходится тест
     */
    public QuizHandler(Integer proglang_id) {
        proglangService = new ProglangService();
        userService = new UserService();
    }


    /**
     * Получение ответа на сообщение в состоянии QUIZ
     * @param message сообщение пользователя
     * @param currentUser пользователь который отправил сообщение
     * @return правильность ответа и следующий вопрос ИЛИ конец квиза
     */
    public String getResponse(String message, User currentUser) {
        message = message.toLowerCase();

        Integer solvedCounter = Integer.parseInt(currentUser.getQurrentQuestion());
        Integer quizStat = Integer.parseInt(currentUser.getCurrentQuizStats());
        Integer quizProglang = Integer.parseInt(currentUser.getCurrentProglang());
        Progquiz currentQuestion = null;
        String response;


        if (solvedCounter != -1) {
            currentQuestion = proglangService.findProgquizzesByProglangId(quizProglang).get(solvedCounter);
        }

        if (quizProglang == -1) {
            response = "Выберите язык программирования";
            quizProglang = 0;
        } else if (message.equals("/stop")) {
            solvedCounter = -1;
            quizStat = -1;
            quizProglang = -1;
            response =  "Тест завершен, чтобы начать заново введите /quiz";
        } else if (solvedCounter == -1) {
            if (proglangService.checkExistenceOfProglang(message) != -1) {
                quizProglang = proglangService.checkExistenceOfProglang(message);
            } else {
                return "Язык программирования не найден";
            }

            solvedCounter = 0;
            quizStat = 0;
            currentQuestion = proglangService.getQuestionByLang(quizProglang, solvedCounter);
            response = "Тест по ЯП JavaScript, состоит из " + proglangService.getSizeOfProglang(quizProglang)
                    + " вопросов\n\n" + currentQuestion.getQuestion();

        } else if (solvedCounter < proglangService.getSizeOfProglang(quizProglang) - 1) {
            String checkResponse = checkCorrectness(message, currentQuestion);
            if (checkResponse.contains("Вы ответили правильно!")) {
                quizStat++;
            }
            currentQuestion = proglangService.getQuestionByLang(quizProglang, ++solvedCounter);
            response =  checkResponse + currentQuestion.getQuestion();
        } else if (solvedCounter == proglangService.getSizeOfProglang(quizProglang) - 1) {
            String checkResponse = checkCorrectness(message, currentQuestion);
            if(checkResponse.contains("Вы ответили правильно!")) {
                quizStat ++;
            }
            String testStats = "Количество правильных ответов:" +
                    quizStat + "/" +
                    proglangService.findProgquizzesByProglangId(quizProglang).size();
            response =  checkResponse + "Тест закончен!\n" + testStats;

            solvedCounter = -1;
            quizStat = -1;
            quizProglang = -1;
        } else {
            throw new RuntimeException("Error in quiz logic");
        }

        currentUser.setQurrentQuestion(Integer.toString(solvedCounter));
        currentUser.setCurrentQuizStats(Integer.toString(quizStat));
        currentUser.setCurrentProglang(Integer.toString(quizProglang));
        userService.update(currentUser);
        return  response;
    }

    /**
     * Проверка правильности ответа пользователя
     * @param answer ответ пользователя
     * @param currentQuestion текущий вопрос
     * @return текстовое описание, правильно ли ответил пользователь или нет
     */
    private String checkCorrectness(String answer, Progquiz currentQuestion) {
        return answer.equals(currentQuestion.getAnswerValue())
            ? "Вы ответили правильно!\n"
            : "Вы ответили неправильно! Правильный ответ:" + currentQuestion.getAnswerValue() + '\n';
    }
}

