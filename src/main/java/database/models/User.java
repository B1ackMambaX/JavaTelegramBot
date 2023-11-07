package database.models;

import database.models.types.Plathform;
import database.models.types.State;

import javax.persistence.*;

/**
 * Модель, отражающая сущности таблицы user
 */
@Entity
@Table(name = "public.user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    @Enumerated(EnumType.STRING)
    @Column (name = "plathform")
    private Plathform plathform;

    @Column (name = "plathform_id")
    private Long plathform_id;

    @Column (name = "plathform_username")
    private String plathform_username;

    @Column (name = "plathform_phone")
    private String plathform_phone;

    @Column (name = "plathform_email")
    private String plathform_email;

    @Enumerated(EnumType.STRING)
    @Column (name = "state")
    private State state;

    @Column (name = "setting_field_1")
    private String currentQuestion;

    @Column (name = "setting_field_2")
    private String currentQuizStats;

    @Column (name = "setting_field_3")
    private String setting_field_3;

    @Column (name = "setting_field_4")
    private String setting_field_4;

    @Column (name = "setting_field_5")
    private String setting_field_5;

    public User(){
    }

    public User(Plathform plathform, Long plathform_id) {
        this.plathform = plathform;
        this.plathform_id = plathform_id;
        this.state = State.IDLE;
        this.currentQuestion = "-1";
        this.currentQuizStats = "-1";
    }

    /**
     * Конструктор для тестов
     * @param plathform платформа
     * @param plathform_id id пользователя на платформе
     * @param currentQuestion текущий вопрос
     */
    public User(Plathform plathform, Long plathform_id, State state, String currentQuestion, String currentQuizStats) {
        this.plathform = plathform;
        this.plathform_id = plathform_id;
        this.state = state;
        this.currentQuestion = currentQuestion;
        this.currentQuizStats = currentQuizStats;
    }

    public Plathform getPlathform() {
        return this.plathform;
    }

    public void setPlathform(Plathform plathform) {
        this.plathform = plathform;
    }

    public Long getPlathform_id() {
        return plathform_id;
    }

    public void setPlathform_id(Long plathform_id) {
        this.plathform_id = plathform_id;
    }

    public String getPlathform_username() {
        return plathform_username;
    }

    public void setPlathform_username(String plathform_username) {
        this.plathform_username = plathform_username;
    }

    public String getPlathform_phone() {
        return plathform_phone;
    }

    public void setPlathform_phone(String plathform_phone) {
        this.plathform_phone = plathform_phone;
    }

    public String getPlathform_email() {
        return plathform_email;
    }

    public void setPlathform_email(String plathform_email) {
        this.plathform_email = plathform_email;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getQurrentQuestion() {
        return currentQuestion;
    }

    public void setQurrentQuestion(String qurrentQuestion) {
        this.currentQuestion = qurrentQuestion;
    }

    public String getCurrentQuizStats() {
        return currentQuizStats;
    }

    public void setCurrentQuizStats(String currentQuizStats) {
        this.currentQuizStats = currentQuizStats;
    }

    public String getSettingField3() {
        return setting_field_3;
    }

    public void setSettingField3(String setting_field_3) {
        this.setting_field_3 = setting_field_3;
    }

    public String getSettingField4() {
        return setting_field_4;
    }

    public void setSettingField4(String setting_field_4) {
        this.setting_field_4 = setting_field_4;
    }

    public String getSettingField5() {
        return setting_field_5;
    }

    public void setSettingField5(String setting_field_5) {
        this.setting_field_5 = setting_field_5;
    }
}


