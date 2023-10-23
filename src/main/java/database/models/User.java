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
}


