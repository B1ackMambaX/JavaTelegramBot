package bot;

import database.models.User;
import logic.Response;

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
    Response getResponse(String message, User currentUser);
}
