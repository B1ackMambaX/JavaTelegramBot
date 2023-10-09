package data;

import java.util.ArrayList;

public class Storage {
    private final ArrayList<Question> questions;
    private final int size;
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
        size = questions.size();
    }

    public Question getQuestionByIndex(int index) {
        return this.questions.get(index);
    }
    public ArrayList<Question> getAllQuestions() {
        return this.questions;
    }
    public int getSize() {
        return this.size;
    }
}
