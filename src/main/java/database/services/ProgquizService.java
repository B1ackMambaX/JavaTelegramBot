package database.services;

import database.dao.ProgquizDao;
import database.models.Progquiz;

import java.util.List;

public class ProgquizService {
    private ProgquizDao progquizDao = new ProgquizDao();

    public ProgquizService() {
    }

    public Progquiz findProgquiz(Integer progquiz_id){
        return progquizDao.findByProgquizId(progquiz_id);
    }

    public void saveProgquiz(Progquiz progquiz) {
        progquizDao.save(progquiz);
    }

    public void deleteProgquiz(Progquiz progquiz) {
        progquizDao.delete(progquiz);
    }

    public void updateProgquiz(Progquiz progquiz) {
        progquizDao.update(progquiz);
    }

    public List<Progquiz> findAllProgquizzes() {
        return progquizDao.findAll();
    }
}
