package data;


/**
 * Вопрос в квизе
 */
public record Question(int id, int langId, String questionText, String questionAnswer, String type) {
    /**
     * @param id             - id вопроса
     * @param langId         - id языка программирования
     * @param questionText   - текст вопроса
     * @param questionAnswer - текст ответа
     * @param type           - тип ответа(text or code)
     */
    public Question {}
}
