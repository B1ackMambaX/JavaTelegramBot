package logic.handlers;

import database.models.*;
import database.models.types.State;
import database.services.ProglangService;
import database.services.StatisticsService;
import database.services.UserService;
import logic.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
* Обработчик квиза
*/
public class QuizHandler {
    private final ProglangService proglangService;
    private final UserService userService;
    private final StatisticsService statisticsService;

    Quizstate userState;

    public QuizHandler() {
        proglangService = new ProglangService();
        userService = new UserService();
        statisticsService = new StatisticsService();
    }


    /**
     * Конструктор для тестов
     * @param proglangService мок сервиса ЯП
     * @param userService мок сервиса пользователя
     */
    public QuizHandler(ProglangService proglangService, UserService userService, Quizstate userState, StatisticsService statisticsService) {
        this.proglangService = proglangService;
        this.userService = userService;
        this.userState = userState;
        this.statisticsService = statisticsService;
    }
    /**
     * Получение ответа на сообщение в состоянии QUIZ
     * @param message сообщение пользователя
     * @param currentUser пользователь который отправил сообщение
     * @return правильность ответа и следующий вопрос ИЛИ конец квиза
     */
    public Response getResponse(String message, User currentUser) {
        userState = userService.getQuizState(currentUser.getId());
        message = message.toLowerCase();


        int solvedCounter = userState.getCurrentQuestionIndex();
        int quizStat = userState.getCurrentQuizStats();
        long quizProglang = userState.getCurrentProglangId();
        Progquiz currentQuestion = null;
        List<String> keyboardMessages = new ArrayList<>();
        Response response;

        if (solvedCounter != -1) {
            currentQuestion = proglangService.getQuestionByLang(quizProglang, solvedCounter);
        }

        if (quizProglang == -1) {
            currentUser.setState(State.QUIZ);
            response = new Response("Выберите язык программирования", proglangService.getAllProglangNames());
            quizProglang = 0;
        } else if (message.equals("/stop")) {
            solvedCounter = -1;
            quizStat = -1;
            quizProglang = -1;
            keyboardMessages = List.of("/help", "/quiz", "/mystats", "/leaderboard");
            response =  new Response("Тест завершен, чтобы начать заново введите /quiz", keyboardMessages);
            currentUser.setState(State.IDLE);
        } else if (solvedCounter == -1) {
            if (proglangService.getProglangIdByName(message) != -1) {
                quizProglang = proglangService.getProglangIdByName(message);
            } else {
                return new Response("Язык программирования не найден", proglangService.getAllProglangNames());
            }

            solvedCounter = 0;
            quizStat = 0;
            currentQuestion = proglangService.getQuestionByLang(quizProglang, solvedCounter);
            String resonseText = "Тест по ЯП " +
                    proglangService.findProglang(quizProglang).getName() + ", состоит из " +
                    proglangService.getSizeOfProglang(quizProglang)
                    + " вопросов\n\n" + currentQuestion.getQuestion();

            keyboardMessages = List.of("/stop");
            response = new Response(resonseText, keyboardMessages);

        } else if (solvedCounter < proglangService.getSizeOfProglang(quizProglang) - 1) {
            String checkResponse = checkCorrectness(message, currentQuestion);
            if (checkResponse.contains("Вы ответили правильно!")) {
                quizStat++;
            }
            currentQuestion = proglangService.getQuestionByLang(quizProglang, ++solvedCounter);
            String responseText =  checkResponse + currentQuestion.getQuestion();
            keyboardMessages= List.of("/stop");
            response = new Response(responseText, keyboardMessages);
        } else if (solvedCounter == proglangService.getSizeOfProglang(quizProglang) - 1) {
            String checkResponse = checkCorrectness(message, currentQuestion);
            if(checkResponse.contains("Вы ответили правильно!")) {
                quizStat ++;
            }
            String testStats = "Количество правильных ответов:" +
                    quizStat + "/" +
                    proglangService.getSizeOfProglang(quizProglang);
            String responseText =  checkResponse + "Тест закончен!\n" + testStats;
            keyboardMessages = List.of("/help", "/quiz", "/mystats", "/leaderboard");
            response = new Response(responseText, keyboardMessages);
            currentUser.setState(State.IDLE);
            statisticsService.saveStatistics(currentUser, quizStat, proglangService.findProglang(quizProglang));

            solvedCounter = -1;
            quizStat = -1;
            quizProglang = -1;
        } else {
            throw new RuntimeException("Error in quiz logic");
        }

        userState.setCurrentQuestionIndex(solvedCounter);
        userState.setCurrentQuizStats(quizStat);
        userState.setCurrentProglangId(quizProglang);

        userService.update(currentUser);
        userService.updateQuizState(userState);
        return response;
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