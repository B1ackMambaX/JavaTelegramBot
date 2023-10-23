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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int proglang_id;

    @Column(name = "proglang_name")
    private String proglang_name;

    @OneToMany(mappedBy = "progquiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Progquiz> progquizs;

    public Proglang() {
    }

    public Proglang(String proglang_name) {
        this.proglang_name = proglang_name;
    }
}
