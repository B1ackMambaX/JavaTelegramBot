package database.models;

import javax.persistence.*;

@Entity
@Table(name = "quizstate")
public class Quizstate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "current_proglang_id")
    private Integer currentProglangId;
    @Column (name = "current_question_index")
    private Integer currentQuestionIndex;

    @Column (name = "current_quiz_stats")
    private Integer currentQuizStats;

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

    public Integer getCurrentProglangId() {
        return currentProglangId;
    }

    public void setCurrentProglangId(Integer currentProglangId) {
        this.currentProglangId = currentProglangId;
    }

    public Integer getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(Integer currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public Integer getCurrentQuizStats() {
        return currentQuizStats;
    }

    public void setCurrentQuizStats(Integer currentQuizStats) {
        this.currentQuizStats = currentQuizStats;
    }
}
