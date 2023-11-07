package logic;

import java.util.List;

/**
 * Ответ на сообщение пользователя
 * @param message текстовое сообщение которое отправляется пользователю
 * @param keyboardMessages сообщения для клавиатуры чат-бот платформы
 */
public record Response(String message, List<String> keyboardMessages) {

}
