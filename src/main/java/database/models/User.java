package database.models;

import database.models.types.Plathform;
import database.models.types.State;

import javax.persistence.*;

/**
 * Модель, отражающая сущности таблицы user
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int user_id;

    @Enumerated(EnumType.STRING)
    @Column (name = "plathform")
    private Plathform plathform;

    @Column (name = "plathform_id")
    private Integer plathform_id;

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
    private String setting_field_1;

    @Column (name = "setting_field_2")
    private String setting_field_2;

    @Column (name = "setting_field_3")
    private String setting_field_3;

    @Column (name = "setting_field_4")
    private String setting_field_4;

    @Column (name = "setting_field_5")
    private String setting_field_5;

    public Plathform getPlathform() {
        return plathform;
    }

    public void setPlathform(Plathform plathform) {
        this.plathform = plathform;
    }

    public Integer getPlathform_id() {
        return plathform_id;
    }

    public void setPlathform_id(Integer plathform_id) {
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

    public String getSetting_field_1() {
        return setting_field_1;
    }

    public void setSetting_field_1(String setting_field_1) {
        this.setting_field_1 = setting_field_1;
    }

    public String getSetting_field_2() {
        return setting_field_2;
    }

    public void setSetting_field_2(String setting_field_2) {
        this.setting_field_2 = setting_field_2;
    }

    public String getSetting_field_3() {
        return setting_field_3;
    }

    public void setSetting_field_3(String setting_field_3) {
        this.setting_field_3 = setting_field_3;
    }

    public String getSetting_field_4() {
        return setting_field_4;
    }

    public void setSetting_field_4(String setting_field_4) {
        this.setting_field_4 = setting_field_4;
    }

    public String getSetting_field_5() {
        return setting_field_5;
    }

    public void setSetting_field_5(String setting_field_5) {
        this.setting_field_5 = setting_field_5;
    }
}


