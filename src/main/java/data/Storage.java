package data;

import data.question.Question;
import data.question.QuestionType;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, который реализует временное хранилище вопросов
 */
public class Storage {
    private final List<Question> questions;
    public Storage() {
        questions = new ArrayList<>();
        questions.add(new Question(0, 0, "Какой метод используется для фильтрации массива?", "filter", QuestionType.TEXT));
        questions.add(new Question(1, 0, "Какое ключевое слово используется для обозначения наследования классов?", "extends", QuestionType.TEXT));
        questions.add(new Question(2, 0, "Какой метод добавляет элемент в конец массива?", "push", QuestionType.TEXT));
        questions.add(new Question(3, 0, "Какой метод удаляет последний элемент массива?", "pop", QuestionType.TEXT));
        questions.add(new Question(4, 0, "Какой метод используется для разбиения строки на массив подстрок?", "split", QuestionType.TEXT));
        questions.add(new Question(5, 0, "Какой метод используется для объединения элементов массива в строку?", "join", QuestionType.TEXT));
        questions.add(new Question(6, 0, "Какой оператор используется для проверки на равенство с учетом типа данных?", "===", QuestionType.TEXT));
        questions.add(new Question(7, 0, "Какой оператор используется для логического И?", "&&", QuestionType.TEXT));
        questions.add(new Question(8, 0, "Какой оператор используется для логического ИЛИ?", "||", QuestionType.TEXT));
        questions.add(new Question(9, 0, "Какой метод используется для поиска индекса элемента в массиве?", "indexof", QuestionType.TEXT));
    }

    public Question getQuestionByIndex(int index) {
        return questions.get(index);
    }
    public List<Question> getAllQuestions() {
        return questions;
    }
    public int getSize() {
        return questions.size();
    }
}
