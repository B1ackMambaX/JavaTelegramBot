package logic;

/**
 * Класс реализующий логику работы бота
 */
public class Logic {
    private boolean isQuiz;
    QuizHandler quiz;

    public Logic() {
        isQuiz = false;
    }

    /**
     * Обработчик сообщений пользователя
     * @param message сообщение пользователя
     * @return ответ в зависимости от сообщения
     */
    public String messageHandler(String message) {
        message = message.toLowerCase();

        if (isQuiz && !message.equals("/help") && !message.equals("/stop")) {
            return quiz.answerHandler(message);
        } else if (message.equals("/start") || message.equals("/help")) {
            return """
                    Привет, я помогу тебе поднять теорию по языкам программирования!
                    Пока я умею не так много, но могу задать тебе вопросы по JavaSctipt.\s
                    В ответ на вопрос присылай мне одно слово.
                    Для начала введи /quiz
                    Чтобы остановить тест введи /stop""";
        } else if (message.equals("/quiz") && !isQuiz) {
            quiz = new QuizHandler(this);
            isQuiz = true;
            return quiz.answerHandler(message);
        } else if (message.equals("/stop") && isQuiz) {
            isQuiz = false;
            return "Тест завершен, чтобы начать заново введите /quiz";
        } else {
            return "Я не понимаю вас, посмотреть список доступных комманд можно с помощью /help";
        }
    }

    /**
     * Метод позволяющий изменять значение isQuiz внутри других классов пакета logic
     * @param isQuiz новое значение
     */
    protected void setQuiz(boolean isQuiz) {
        this.isQuiz = isQuiz;
    }
}
