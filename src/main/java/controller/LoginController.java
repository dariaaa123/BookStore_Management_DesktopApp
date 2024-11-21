package controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.User;
import model.validator.UserValidator;
import service.user.AuthentificationService;
import view.LoginView;
import java.util.List;

public class LoginController {

    private final LoginView loginView;
    private final AuthentificationService authenticationService;
    private final UserValidator userValidator;


    public LoginController(LoginView loginView, AuthentificationService authenticationService,UserValidator userValidator) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            User user = authenticationService.login(username, password);
            if (user == null)
            {
                loginView.setActionTargetText("Invalid Username or password!");;

            }else
            {
                loginView.setActionTargetText("LogIn successful");
            }

        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            userValidator.validate(username,password);
            final List<String> errors = userValidator.getErrors();
            if(errors.isEmpty())
            {
                if(authenticationService.register(username,password))
                {
                    loginView.setActionTargetText("Register successful!");
                }
                else
                {
                    loginView.setActionTargetText("Register NOT successful!");
                }
            }
            else {
                loginView.setActionTargetText(userValidator.getFormattedErrors());
            }


        }
    }
}
