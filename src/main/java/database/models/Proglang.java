package database.models;

import javax.persistence.*;
import java.util.List;

/**
 * Модель, отражающая сущности таблицы proglang
 */
@Entity
@Table(name = "proglang")
public class Proglang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "proglang_name")
    private String proglang_name;

    @OneToMany(mappedBy = "proglang", cascade = CascadeType.ALL)
    private List<Progquiz> progquizs;

    public Proglang() {
    }

    /**
     * Конструктор для тестов
     * @param proglang_name имя ЯП
     * @param id id ЯП
     */
    public Proglang(String proglang_name, int id) {
        this.id = id;
        this.proglang_name = proglang_name;
    }

    public Integer getId() {
        return id;
    }

    public String getName(){
        return proglang_name;
    }

    public void setName(String name){
        this.proglang_name = name;
    }

    @Override
    public String toString() {
        return "Proglang:" + id + " " + proglang_name;
    }
}
