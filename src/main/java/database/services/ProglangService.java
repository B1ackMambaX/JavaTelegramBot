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
     *
     * @param proglang_id id языка программирования
     * @return количество вопросов по выбранному ЯП
     */
    public Long getSizeOfProglang(Integer proglang_id) {
        return proglangDao.countProgquizzesByProglangId(proglang_id);
    }

    /**
     * Получение конкретного вопроса по ЯП
     * @param proglang_id id ЯП
     * @param offset отступ(начиная с 0, может быть null)
     * @param limit сколько вопросов(может быть null)
     * @return вопрос
     */
    public Progquiz getQuestionByLang(Integer proglang_id, int offset) {
        return proglangDao.findProgquizByProglangId(proglang_id, offset);
    }

    /**
     * Получение id ЯП по его имени
     * @param proglang_name имя ЯП
     * @return id ЯП, либо -1 если ЯП не существует
     */
    public int getProglangIdByName(String proglang_name) {
        return proglangDao.getIdByName(proglang_name);
    }

    /**
     * Получение имен всех ЯП в БД
     * @return список имен ЯП
     */
    public List<String> getAllProglangNames() {
        return proglangDao.getAllNames();
    }
}
