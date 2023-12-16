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

    public Proglang findProglang(long proglangId) {
        return proglangDao.findByProglangId(proglangId);
    }

    /**
     * Получение количества вопросов в зависимости от ЯП
     *
     * @param proglangId id языка программирования
     * @return количество вопросов по выбранному ЯП
     */
    public long getSizeOfProglang(long proglangId) {
        return proglangDao.countProgquizzesByProglangId(proglangId);
    }

    /**
     * Получение конкретного вопроса по ЯП
     * @param proglangId id ЯП
     * @param offset отступ(начиная с 0, может быть null)
     * @return вопрос
     */
    public Progquiz getQuestionByLang(long proglangId, int offset) {
        return proglangDao.findProgquizByProglangId(proglangId, offset);
    }

    /**
     * Получение id ЯП по его имени
     * @param proglangName имя ЯП
     * @return id ЯП, либо -1 если ЯП не существует
     */
    public long getProglangIdByName(String proglangName) {
        return proglangDao.getIdByName(proglangName);
    }

    /**
     * Получение имен всех ЯП в БД
     * @return список имен ЯП
     */
    public List<String> getAllProglangNames() {
        return proglangDao.getAllNames();
    }
}
