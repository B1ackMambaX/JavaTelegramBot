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
    private Integer id;

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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Quizstate quizstate;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Statistics statistics;

    public User(){
    }

    public User(Plathform plathform, Long plathform_id) {
        this.plathform = plathform;
        this.plathform_id = plathform_id;
        this.state = State.IDLE;
    }

    /**
     * Конструктор для тестов
     * @param plathform платформа
     * @param plathform_id id пользователя на платформе
     * @param state состояние пользователя
     */
    public User(Plathform plathform, Long plathform_id, State state) {
        this.plathform = plathform;
        this.plathform_id = plathform_id;
        this.state = state;
    }

    public Integer getId() {
        return this.id;
    }

    public Plathform getPlathform() {
        return this.plathform;
    }

    public void setPlathform(Plathform plathform) {
        this.plathform = plathform;
    }

    public Long getPlathform_id() {
        return this.plathform_id;
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
}


