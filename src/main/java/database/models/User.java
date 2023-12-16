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
    private long id;

    @Enumerated(EnumType.STRING)
    @Column (name = "plathform")
    private Plathform plathform;

    @Column (name = "plathform_id")
    private long plathformId;

    @Column (name = "plathform_username")
    private String plathformUsername;

    @Column (name = "plathform_phone")
    private String plathformPhone;

    @Column (name = "plathform_email")
    private String plathformEmail;

    @Enumerated(EnumType.STRING)
    @Column (name = "state")
    private State state;

    public User(){
    }

    public User(Plathform plathform, long plathformId, String plathformUsername) {
        this.plathform = plathform;
        this.plathformId = plathformId;
        this.state = State.IDLE;
        this.plathformUsername = plathformUsername;
    }

    /**
     * Конструктор для тестов
     * @param id айди пользователя в БД
     * @param plathform платформа
     * @param plathformId id пользователя на платформе
     * @param state состояние пользователя
     * @param plathformUsername имя пользователя на платформе
     */
    public User(long id, Plathform plathform, long plathformId, State state, String plathformUsername) {
        this.id = id;
        this.plathform = plathform;
        this.plathformId = plathformId;
        this.state = state;
        this.plathformUsername = plathformUsername;
    }

    public long getId() {
        return this.id;
    }

    public Plathform getPlathform() {
        return this.plathform;
    }

    public void setPlathform(Plathform plathform) {
        this.plathform = plathform;
    }

    public long getPlathformId() {
        return this.plathformId;
    }

    public void setPlathformId(long plathformId) {
        this.plathformId = plathformId;
    }

    public String getPlathformUsername() {
        return plathformUsername;
    }

    public void setPlathformUsername(String plathformUsername) {
        this.plathformUsername = plathformUsername;
    }

    public String getPlathformPhone() {
        return plathformPhone;
    }

    public void setPlathformPhone(String plathformPhone) {
        this.plathformPhone = plathformPhone;
    }

    public String getPlathformEmail() {
        return plathformEmail;
    }

    public void setPlathformEmail(String plathformEmail) {
        this.plathformEmail = plathformEmail;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}


