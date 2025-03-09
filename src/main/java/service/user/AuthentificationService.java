package service.user;

import model.User;
import model.validator.Notification;

public interface AuthentificationService {
    Notification<Boolean> register(String username,String password,String role);
    Notification<User> login(String ursername, String password);
    boolean logout(User user);
    String getRoleFromUser(String username, String password);
}
