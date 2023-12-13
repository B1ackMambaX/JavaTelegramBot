package database.models;

import javax.persistence.*;

/**
 * Модель, отражающая сущности таблицы statistics
 */
@Entity
@Table(name = "statistics")
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proglang_id")
    private Proglang proglang;

    @Column(name = "score")
    private Integer score;

    public Statistics(User user, Proglang proglang, Integer score) {
        this.score = score;
        this.user = user;
        this.proglang = proglang;
    }

    public Statistics() {}

    public Integer getId() {
        return id;
    }

    public User getUser(){
        return user;
    }

    public Proglang getProglang(){
        return proglang;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer new_score) {
        this.score = new_score;
    }

                            public String toString() {
        return "Score:" + user.getId() + " " + proglang.getName() + " " + score;
    }
}
