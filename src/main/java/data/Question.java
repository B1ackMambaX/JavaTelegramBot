package data;


public class Question {
    private final int id;
    private final int langId;
    private final String questionText;
    private final String questionAnswer;
    private final String type;

    public Question(int id, int langId, String questionText, String questionAnswer, String type) {
        this.id = id;
        this.langId = langId;
        this.questionText = questionText;
        this.questionAnswer = questionAnswer;
        this.type = type;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public String getQuestionAnswer() {
        return this.questionAnswer;
    }

    public int getId() {
        return this.id;
    }

    public int getLangId() {
        return this.langId;
    }

    public String getType() {
        return this.type;
    }

    public String checkCorrectness(String answer) {
        return answer.equals(this.questionAnswer) ? "Вы ответили правильно!\n" :
                "Вы ответили неправильно!\n";
    }
}
