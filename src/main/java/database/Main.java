package database;

import database.models.Proglang;
import database.models.Progquiz;
import database.services.ProglangService;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        ProglangService proglangService = new ProglangService();
        // Proglang proglang = new Proglang("python");
        // proglangService.saveProglang(proglang);
        Proglang proglang = proglangService.findProglang(1);
        System.out.println(proglang);
        proglang.setName("python");
        proglangService.updateProglang(proglang);
        Proglang proglang1 = proglangService.findProglang(1);
        System.out.println(proglang1);
        List<Proglang> proglangs = proglangService.findAllProglangs();
        System.out.println(proglangs);
        proglangService.deleteProglang(proglang);
        List<Progquiz> progquizs = proglangService.findProgquizzesByProglangId(proglang.getId());
        System.out.println(progquizs);

    }
}
