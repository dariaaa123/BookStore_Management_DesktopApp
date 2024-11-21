import controller.LoginController;
import database.JDBConnectionWrapper;
import javafx.application.Application;
import javafx.stage.Stage;
import model.validator.UserValidator;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.user.AuthentificationServiceImpl;
import service.user.AuthentificationService;
import view.LoginView;
import java.sql.Connection;
import static database.Constants.Schemas.PRODUCTION;

public class Main extends Application {
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();
       final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
       final UserRepository userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
       final AuthentificationService authentificationService = new AuthentificationServiceImpl(userRepository,rightsRolesRepository);

       final LoginView loginView = new LoginView(primaryStage);
       final UserValidator userValidator = new UserValidator(userRepository);
       new LoginController(loginView,authentificationService,userValidator);

    }
}