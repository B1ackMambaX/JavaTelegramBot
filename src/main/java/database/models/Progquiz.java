package database.models;

import database.models.types.AnswerType;

import javax.persistence.*;

/**
 * Модель, отражающая сущности таблицы progquiz
 */
@Entity
@Table(name = "progquiz")
public class Progquiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proglangId")
    private Proglang proglang;

    @Column (name = "question")
    private String question;

    @Enumerated(EnumType.STRING)
    @Column (name = "answerType")
    private AnswerType answerType;
    @Column (name = "answerValue")
    private String answerValue;

    public Progquiz() {}

    /**
     * Конструктор для тестов
     * @param question Вопрос
     * @param answerValue Ответ
     */
    public Progquiz(String question, String answerValue) {
        proglang = new Proglang();
        this.question = question;
        answerType = AnswerType.TEXT;
        this.answerValue = answerValue;
    }

    public Proglang getProglang() {
        return proglang;
    }

    public void setProglang(Proglang proglang) {
        this.proglang = proglang;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    public String getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(String answerValue) {
        this.answerValue = answerValue;
    }
}
