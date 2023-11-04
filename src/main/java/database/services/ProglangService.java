package database.services;

import database.dao.ProglangDao;
import database.models.Proglang;
import database.models.Progquiz;

import java.util.List;

/**
 * Слой сервиса который предоставляет CRUD операции для таблицы языков программирования
 */
public class ProglangService {
    private ProglangDao proglangDao = new ProglangDao();

    public ProglangService() {
    }

    public Proglang find(Integer proglang_id){
        return proglangDao.findByProglangId(proglang_id);
    }

    public void save(Proglang proglang) {
        proglangDao.save(proglang);
    }

    public void delete(Proglang proglang) {
        proglangDao.delete(proglang);
    }

    public void update(Proglang proglang) {
        proglangDao.update(proglang);
    }

    public List<Proglang> findAll() {
        return proglangDao.findAll();
    }

    public List<Progquiz> findProgquizzesByProglangId(Integer proglang_id) {
        return proglangDao.findProgquizzesByProglangId(proglang_id);
    }
}
