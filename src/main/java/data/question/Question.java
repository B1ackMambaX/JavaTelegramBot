package data.question;


/**
 * Вопрос в квизе
 */
public record Question(int id, int langId, String questionText, String questionAnswer, QuestionType type) {
    /**
     * @param id - id вопроса
     * @param langId - id языка программирования
     * @param questionText - текст вопроса
     * @param questionAnswer - текст ответа
     * @param type - тип ответа
     */
    public Question {}
}
