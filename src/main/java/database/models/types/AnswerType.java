package database.models.types;

/**
 * Тип вопроса
 */
public enum AnswerType {
    /**
     * Вопрос в ответ на который нужно прислать строку
     */
    TEXT,
    /**
     * Вопрос в ответ на который нужно прислать фрагмент кода
     */
    CODE
}