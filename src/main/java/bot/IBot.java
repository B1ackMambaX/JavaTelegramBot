package bot;

import data.User;

public interface IBot {
    String parseMessage(String message, User currentUser);
    Object initKeyboard(User user);
    Object login(long chatId);
}
