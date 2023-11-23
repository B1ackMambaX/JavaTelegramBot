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

    public List<Progquiz> findProgquizzesByProglangId(Integer proglang_id) {
        return proglangDao.findProgquizzesByProglangId(proglang_id);
    }

    public int getSizeOfProglang(Integer proglang_id) {
        return proglangDao.findProgquizzesByProglangId(proglang_id).size();
    }

    public Progquiz getQuestionByLang(Integer progalng_id, int index) {
        return proglangDao.findProgquizzesByProglangId(progalng_id).get(index);
    }

    public int checkExistenceOfProglang(String proglang_name) {
        final List<Proglang> proglangs = proglangDao.findAll();
        for(Proglang lang : proglangs) {
            if (lang.getName().toLowerCase().equals(proglang_name)) {
                return lang.getId();
            }
        }
        return -1;
    }
}
