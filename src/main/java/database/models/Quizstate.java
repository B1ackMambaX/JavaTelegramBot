package database.models;

import javax.persistence.*;

@Entity
@Table(name = "quizstate")
public class Quizstate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "current_proglang_id")
    private long currentProglangId;
    @Column (name = "current_question_index")
    private int currentQuestionIndex;

    @Column (name = "current_quiz_stats")
    private int currentQuizStats;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Quizstate(){
    }

    public Quizstate(User user) {
        this.user = user;
        this.currentProglangId = -1;
        this.currentQuestionIndex = -1;
        this.currentQuizStats = -1;
    }

    public long getCurrentProglangId() {
        return currentProglangId;
    }

    public void setCurrentProglangId(long currentProglangId) {
        this.currentProglangId = currentProglangId;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public int getCurrentQuizStats() {
        return currentQuizStats;
    }

    public void setCurrentQuizStats(int currentQuizStats) {
        this.currentQuizStats = currentQuizStats;
    }
}
