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
}
