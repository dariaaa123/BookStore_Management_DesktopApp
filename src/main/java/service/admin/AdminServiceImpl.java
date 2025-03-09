package service.admin;

import model.Role;
import model.User;
import model.validator.Notification;
import repository.admin.AdminRepository;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.util.List;

public class AdminServiceImpl implements AdminService{
    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public boolean save(User user) {
        return this.adminRepository.save(user);

    }

    @Override
    public boolean delete(User user) {
        return this.adminRepository.delete(user);
    }

    @Override
    public boolean generateReport() {
        return false;
    }

    @Override
    public List<User> findAll() {
        return this.adminRepository.findAll();
    }

    @Override
    public Notification<User> findByUsername(String username)
    {
        return this.adminRepository.findByUsername(username);
    }

    @Override
    public Notification<Role> findRoleId(String role) {
        return this.adminRepository.findRoleId(role);
    }

    @Override
    public boolean saveRole(Long userId, Long roleId) {
        return this.adminRepository.saveRole(userId,roleId);
    }
}
