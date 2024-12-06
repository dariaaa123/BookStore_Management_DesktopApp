package launcher;

import controller.AdminController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import model.User;
import repository.admin.AdminRepository;
import repository.admin.AdminRepositoryMySQL;
import repository.pdf.PDFGenerateRepo;
import repository.pdf.PDFGenerateMySQLRepo;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.admin.AdminService;
import service.admin.AdminServiceImpl;
import service.pdfGenerator.PDFService;
import service.pdfGenerator.PDFServiceImpl;
import service.user.AuthentificationService;
import service.user.AuthentificationServiceImpl;
import view.AdminView;
import java.sql.Connection;
import java.util.List;

public class AdminComponentFactory {
    private final AdminView adminView;
    private final AdminController adminController;
    private final AdminRepository adminRepository;
    private final AdminService adminService;
    private final UserRepository userRepository;
    private final AuthentificationService authentificationService;
    private final RightsRolesRepository rightsRolesRepository;
    private static volatile AdminComponentFactory instance;
    private final PDFService pdfGenerateService;
    private final PDFGenerateRepo pdfGenerateRepo;

    public static AdminComponentFactory getInstance(Boolean componentsForTest, Stage stage){
        if (instance == null) {

            synchronized (AdminComponentFactory.class) {
                if (instance == null) {
                    instance = new AdminComponentFactory(componentsForTest, stage);
                }
            }
        }

        return instance;
    }
    private AdminComponentFactory(Boolean componentsForTest, Stage stage){

        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.pdfGenerateRepo = new PDFGenerateMySQLRepo(connection);
        this.pdfGenerateService = new PDFServiceImpl(pdfGenerateRepo,connection);
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
        this.authentificationService = new AuthentificationServiceImpl(userRepository,rightsRolesRepository);
        this.adminRepository = new AdminRepositoryMySQL(connection,authentificationService);
        this.adminService = new AdminServiceImpl(adminRepository);
        List<User> users = this.adminService.findAll();
        this.adminView = new AdminView(stage, users);
        this.adminController = new AdminController(adminView, adminService,authentificationService,pdfGenerateService);
    }

}
