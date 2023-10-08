package logic;
import data.Storage;
import data.Question;

public class Logic {
    private boolean isQuiz;
    private Storage questions;
    private int solvedCounter;
    private Question currentQuestion;

    public Logic() {
        isQuiz = false;
        solvedCounter = -1;
    }
    public String messageHandler(String message) {
        message = message.toLowerCase();

        if (isQuiz && !message.equals("/help") && !message.equals("/stop")) {
            return quizHandler(message);
        } else if (message.equals("/start") || message.equals("/help")) {
            return """
                    Привет, я помогу тебе поднять теорию по языкам программирования!
                    Пока я умею не так много, но могу задать тебе вопросы по JavaSctipt.\s
                    В ответ на вопрос присылай мне одно слово.
                    Для начала введи /quiz
                    Чтобы остановить тест введи /stop""";
        } else if (message.equals("/quiz") && !isQuiz) {
            isQuiz = true;
            return quizHandler(message);
        } else if (message.equals("/stop") && isQuiz) {
            isQuiz = false;
            solvedCounter = -1;
            return "Тест завершен, чтобы начать заново введите /quiz";
        } else {
            return "Я не понимаю вас, посмотреть список доступных комманд можно с помощью /help";
        }
    }

    private String quizHandler(String message) {
        if (solvedCounter == -1) {
            questions = new Storage();
            solvedCounter = 0;
            currentQuestion = questions.getQuestionByIndex(solvedCounter);
            return currentQuestion.getQuestionText();
        } else if (solvedCounter < questions.getSize() - 1) {
            String checkResponse = currentQuestion.checkCorrectness(message);
            currentQuestion = questions.getQuestionByIndex(++solvedCounter);
            return checkResponse + currentQuestion.getQuestionText();
        } else if (solvedCounter == questions.getSize() - 1) {
            isQuiz = false;
            solvedCounter = -1;
            String checkResponse = currentQuestion.checkCorrectness(message);
            return checkResponse + "Тест закончен!";
        } else {
            throw new RuntimeException("Error in quiz logic");
        }
    }
}
