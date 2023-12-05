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
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proglang_id")
    private Proglang proglang;

    @Column (name = "question")
    private String question;

    @Enumerated(EnumType.STRING)
    @Column (name = "answer_type")
    private AnswerType answer_type;
    @Column (name = "answer_value")
    private String answer_value;

    public Progquiz() {}

    /**
     * Конструктор для тестов
     * @param question Вопрос
     * @param answer_value Ответ
     */
    public Progquiz(String question, String answer_value, Proglang proglang) {
        this.proglang = proglang;
        this.question = question;
        answer_type = AnswerType.TEXT;
        this.answer_value = answer_value;
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
        return answer_type;
    }

    public void setAnswerType(AnswerType answer_type) {
        this.answer_type = answer_type;
    }

    public String getAnswerValue() {
        return answer_value;
    }

    public void setAnswerValue(String answer_value) {
        this.answer_value = answer_value;
    }
}
