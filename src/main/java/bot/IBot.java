package bot;

import database.models.User;
import database.models.types.Plathform;

public interface IBot {
    String parseMessage(String message, User currentUser);
    Object initKeyboard(User user);
    Object login(long chatId);
}
