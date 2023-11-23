package database.services;

import database.dao.ProglangDao;
import database.models.Proglang;
import database.models.Progquiz;

import java.util.ArrayList;
import java.util.List;

/**
 * Слой сервиса который предоставляет CRUD операции для таблицы языков программирования
 */
public class ProglangService {
    private ProglangDao proglangDao = new ProglangDao();

    public ProglangService() {
    }

    public Proglang findProglang(Integer proglang_id) {
        return proglangDao.findByProglangId(proglang_id);
    }

    /**
     * Получение количества вопросов в зависимости от ЯП
     * @param proglang_id id языка программирования
     * @return количество вопросов по выбранному ЯП
     */
    public int getSizeOfProglang(Integer proglang_id) {
        return proglangDao.findProgquizzesByProglangId(proglang_id).size();
    }

    /**
     * Получение конкретного вопроса по ЯП
     * @param progalng_id id ЯП
     * @param index номер вопроса
     * @return вопрос
     */
    public Progquiz getQuestionByLang(Integer progalng_id, int index) {
        return proglangDao.findProgquizzesByProglangId(progalng_id).get(index);
    }

    /**
     * Проверка на существование в БД выбранного ЯП
     * @param proglang_name имя ЯП
     * @return id ЯП, либо -1 если ЯП не существует
     */
    public int checkExistenceOfProglang(String proglang_name) {
        final List<Proglang> proglangs = proglangDao.findAll();
        for(Proglang lang : proglangs) {
            if (lang.getName().toLowerCase().equals(proglang_name)) {
                return lang.getId();
            }
        }
        return -1;
    }

    /**
     * Получение имен всех ЯП в БД
     * @return список имен ЯП
     */
    public List<String> getAllProglangNames() {
        final List<Proglang> proglangs = proglangDao.findAll();
        final List<String> names = new ArrayList<>();
        for(Proglang lang: proglangs) {
            names.add(lang.getName());
        }
        return names;
    }
}
