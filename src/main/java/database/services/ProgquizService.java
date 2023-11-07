package database.services;

import database.dao.ProgquizDao;
import database.models.Progquiz;

import java.util.List;

/**
 * Слой сервиса который предоставляет CRUD операции для таблицы вопросов
 */
public class ProgquizService {
    private ProgquizDao progquizDao = new ProgquizDao();

    public ProgquizService() {
    }

    public Progquiz findById(Integer progquiz_id){
        return progquizDao.findByProgquizId(progquiz_id);
    }

    public void save(Progquiz progquiz) {
        progquizDao.save(progquiz);
    }

    public void delete(Progquiz progquiz) {
        progquizDao.delete(progquiz);
    }

    public void update(Progquiz progquiz) {
        progquizDao.update(progquiz);
    }

    public List<Progquiz> findAll() {
        return progquizDao.findAll();
    }
}
