package service.admin;

import model.Role;
import model.User;
import model.validator.Notification;

import java.util.List;

public interface AdminService {
    boolean save(User user);
    boolean delete(User user);
    boolean generateReport();
    List<User> findAll();
    Notification<User> findByUsername(String username);
    Notification<Role> findRoleId(String role);
    boolean saveRole(Long userId, Long roleId);

}
