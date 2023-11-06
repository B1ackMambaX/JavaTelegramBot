package bot;

import database.models.User;

public interface IBot {
    String parseMessage(String message, User currentUser);
    Object initKeyboard(User user);
}
