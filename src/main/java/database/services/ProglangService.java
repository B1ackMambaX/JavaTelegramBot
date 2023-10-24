package database.services;

import database.dao.ProglangDao;
import database.models.Proglang;
import database.models.Progquiz;

import java.util.List;

public class ProglangService {
    private ProglangDao proglangDao = new ProglangDao();

    public ProglangService() {
    }

    public Proglang findProglang(Integer proglang_id){
        return proglangDao.findByProglangId(proglang_id);
    }

    public void saveProglang(Proglang proglang) {
        proglangDao.save(proglang);
    }

    public void deleteProglang(Proglang proglang) {
        proglangDao.delete(proglang);
    }

    public void updateProglang(Proglang proglang) {
        proglangDao.update(proglang);
    }

    public List<Proglang> findAllProglangs() {
        return proglangDao.findAll();
    }

    public List<Progquiz> findProgquizzesByProglangId(Integer proglang_id) {
        return proglangDao.findProgquizzesByProglangId(proglang_id);
    }
}
