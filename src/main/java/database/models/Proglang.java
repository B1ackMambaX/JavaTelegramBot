package database.models;

import javax.persistence.*;

/**
 * Модель, отражающая сущности таблицы proglang
 */
@Entity
@Table(name = "proglang")
public class Proglang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "proglang_name")
    private String proglangName;

    public Proglang() {
    }

    /**
     * Конструктор для тестов
     * @param proglangName имя ЯП
     * @param id id ЯП
     */
    public Proglang(String proglangName, int id) {
        this.id = id;
        this.proglangName = proglangName;
    }

    public long getId() {
        return id;
    }

    public String getName(){
        return proglangName;
    }

    public void setName(String name){
        this.proglangName = name;
    }

    @Override
    public String toString() {
        return "Proglang:" + id + " " + proglangName;
    }
}
