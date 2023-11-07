package bot;

import database.models.User;

import java.util.List;

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
    Object getResponse(String message, User currentUser);
    /**
     * Метод, который инициализирует клавиатуру с кнопками на чат-бот платформе
     * @param keyboardText объект сообщения
     * @return объект разметки клавиатуры
     */
    Object initKeyboard(List<String> keyboardText);
}
