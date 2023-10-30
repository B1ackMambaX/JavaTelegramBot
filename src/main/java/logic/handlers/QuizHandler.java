package logic.handlers;

import database.models.User;
import database.models.Progquiz;
import database.services.ProglangService;
import database.services.UserService;

import java.util.List;

/**
* Обработчик квиза
*/
public class QuizHandler {
    private final ProglangService proglangService = new ProglangService();
    private final UserService userService = new UserService();
    private List<Progquiz> progquizStorage;

    /**
     * @param proglang_id id языка программирования по которому проходится тест
     */
    public QuizHandler(Integer proglang_id) {
        this.progquizStorage = proglangService.findProgquizzesByProglangId(proglang_id);
    }

    /**
     * Обработчик квиза (сообщений в состоянии QUIZ)
     * Для хранения id последнего вопроса в квизе используется setting_field_1 в User
     * @param message сообщение пользователя
     * @param currentUser пользователь который отправил сообщение
     * @return правильность ответа и следующий вопрос ИЛИ конец квиза
     */
    public String answerHandler(String message, User currentUser) {
        message = message.toLowerCase();

        Integer solvedCounter = Integer.parseInt(currentUser.getSettingField1());
        Progquiz currentQuestion = null;
        String response;

        if (solvedCounter != -1) {
            currentQuestion = progquizStorage.get(solvedCounter);
        }

        if(message.equals("/stop")) {
            solvedCounter = -1;
            response =  "Тест завершен, чтобы начать заново введите /quiz";
        } else if (solvedCounter == -1) {
            solvedCounter = 0;
            currentQuestion = progquizStorage.get(solvedCounter);
            response = "Тест по ЯП JavaScript, состоит из " + progquizStorage.size()
                    + " вопросов\n\n" + currentQuestion.getQuestion();

        } else if (solvedCounter < progquizStorage.size() - 1) {
            String checkResponse = checkCorrectness(message, currentQuestion);
            currentQuestion = progquizStorage.get(++solvedCounter);
            response =  checkResponse + currentQuestion.getQuestion();
        } else if (solvedCounter == progquizStorage.size() - 1) {
            solvedCounter = -1;
            String checkResponse = checkCorrectness(message, currentQuestion);
            response =  checkResponse + "Тест закончен!";
        } else {
            throw new RuntimeException("Error in quiz logic");
        }

        currentUser.setSettingField1(Integer.toString(solvedCounter));
        userService.updateUser(currentUser);
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

