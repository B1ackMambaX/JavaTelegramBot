package bot;

import database.models.User;

/**
 * Интерфейс бота
 */
public interface IBot {
    /**
     * Метод, который обрабатывает входящие сообщение и прислыает ответ
     * @param message сообщение
     * @param currentUser пользователь, от которого пришло сообщение
     * @return ответ на сообщение
     */
    String getResponse(String message, User currentUser);

    /**
     * Метод, который инициализирует клавиатуру с кнопками на чат-бот платформе
     * @param user пользователь, от которого пришло сообщение
     * @return объект разметки клавиатуры
     */
    Object initKeyboard(User user);
}
