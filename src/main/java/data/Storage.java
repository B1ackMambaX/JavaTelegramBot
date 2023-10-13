package data;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, который реализует временное хранилище вопросов
 */
public class Storage {
    private final List<Question> questions;
    public Storage() {
        questions = new ArrayList<>();
        questions.add(new Question(0, 0, "Какой метод используется для фильтрации массива?", "filter", "text"));
        questions.add(new Question(1, 0, "Какое ключевое слово используется для обозначения наследования классов?", "extends", "text"));
        questions.add(new Question(2, 0, "Какой метод добавляет элемент в конец массива?", "push", "text"));
        questions.add(new Question(3, 0, "Какой метод удаляет последний элемент массива?", "pop", "text"));
        questions.add(new Question(4, 0, "Какой метод используется для разбиения строки на массив подстрок?", "split", "text"));
        questions.add(new Question(5, 0, "Какой метод используется для объединения элементов массива в строку?", "join", "text"));
        questions.add(new Question(6, 0, "Какой оператор используется для проверки на равенство с учетом типа данных?", "===", "text"));
        questions.add(new Question(7, 0, "Какой оператор используется для логического И?", "&&", "text"));
        questions.add(new Question(8, 0, "Какой оператор используется для логического ИЛИ?", "||", "text"));
        questions.add(new Question(9, 0, "Какой метод используется для поиска индекса элемента в массиве?", "indexof", "text"));
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
