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
    private int progquiz_id;

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
}
