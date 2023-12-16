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
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proglang_id")
    private Proglang proglang;

    @Column(name = "score")
    private int score;

    public Statistics(User user, Proglang proglang, int score) {
        this.score = score;
        this.user = user;
        this.proglang = proglang;
    }

    public Statistics() {}

    public long getId() {
        return id;
    }

    public User getUser(){
        return user;
    }

    public Proglang getProglang(){
        return proglang;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int new_score) {
        this.score = new_score;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "id=" + id +
                ", user=" + user.getPlathform_id() +
                ", proglang=" + proglang.getName() +
                ", score=" + score +
                '}';
    }
}
