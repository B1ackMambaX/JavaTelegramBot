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
    private int proglang_id;

    @Column(name = "proglang_name")
    private String proglang_name;

    @OneToMany(mappedBy = "proglang", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Progquiz> progquizs;

    public Proglang() {
    }

    /**
     * @param proglang_name
     */
    public Proglang(String proglang_name, int proglang_id) {
        this.proglang_name = proglang_name;
        this.proglang_id = proglang_id;
    }

    public Integer getId() {
        return proglang_id;
    }

    public String getName(){
        return proglang_name;
    }

    public void setName(String name){
        this.proglang_name = name;
    }

    @Override
    public String toString() {
        return "Proglang:" + proglang_id + " " + proglang_name;
    }
}
