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
    private List<Progquiz> progquizStorage;

    /**
     * @param proglang_id id языка программирования по которому проходится тест
     */
    public QuizHandler(Integer proglang_id) {
        proglangService = new ProglangService();
        userService = new UserService();

        this.progquizStorage = proglangService.findProgquizzesByProglangId(proglang_id);
    }

    /**
     * Конструктор который используется только в тестах
     * @param proglangService мок сервиса proglang
     * @param userService мок сервиса user
     * @param proglang_id айди языка программирования
     */
    protected QuizHandler(ProglangService proglangService, UserService userService, Integer proglang_id) {
        this.proglangService = proglangService;
        this.userService = userService;
        this.progquizStorage = new ArrayList<>();
        progquizStorage.add(new Progquiz("Какой метод используется для фильтрации массива?", "filter"));
        progquizStorage.add(new Progquiz("Какое ключевое слово используется для обозначения наследования классов?",
                "extends"));
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
        Progquiz currentQuestion = null;
        String response;

        System.out.println(quizStat);

        if (solvedCounter != -1) {
            currentQuestion = progquizStorage.get(solvedCounter);
        }

        if(message.equals("/stop")) {
            solvedCounter = -1;
            quizStat = -1;
            response =  "Тест завершен, чтобы начать заново введите /quiz";
        } else if (solvedCounter == -1) {
            solvedCounter = 0;
            quizStat = 0;
            currentQuestion = progquizStorage.get(solvedCounter);
            response = "Тест по ЯП JavaScript, состоит из " + progquizStorage.size()
                    + " вопросов\n\n" + currentQuestion.getQuestion();

        } else if (solvedCounter < progquizStorage.size() - 1) {
            String checkResponse = checkCorrectness(message, currentQuestion);
            if (checkResponse.contains("Вы ответили правильно!")) {
                quizStat++;
            }
            currentQuestion = progquizStorage.get(++solvedCounter);
            response =  checkResponse + currentQuestion.getQuestion();
        } else if (solvedCounter == progquizStorage.size() - 1) {
            String checkResponse = checkCorrectness(message, currentQuestion);
            if(checkResponse.contains("Вы ответили правильно!")) {
                quizStat ++;
            }

            String testStats = "Количество правильных ответов:" + quizStat + "/" + progquizStorage.size();
            response =  checkResponse + "Тест закончен!" + "\n" + testStats;

            solvedCounter = -1;
            quizStat = -1;
        } else {
            throw new RuntimeException("Error in quiz logic");
        }

        currentUser.setQurrentQuestion(Integer.toString(solvedCounter));
        currentUser.setCurrentQuizStats(Integer.toString(quizStat));
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

