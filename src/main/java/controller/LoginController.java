package controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import launcher.AdminComponentFactory;
import launcher.EmployeeComponentFactory;
import model.User;
import model.validator.Notification;
import model.validator.UserValidator;
import service.user.AuthentificationService;
import service.user.AuthentificationServiceImpl;
import view.LoginView;
import java.util.List;

import static database.Constants.Roles.*;

public class LoginController {

    private final LoginView loginView;
    private final AuthentificationService authenticationService;
    private final Boolean componentsForTest;


    public LoginController(LoginView loginView, AuthentificationService authenticationService,Boolean componentsForTest) {
        this.componentsForTest = componentsForTest;
        this.loginView = loginView;
        this.authenticationService = authenticationService;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();


            Notification<User> loginNotification = authenticationService.login(username, password);
            if (loginNotification.hasErrors())
            {
                loginView.setActionTargetText(loginNotification.getFormattedErrors());

            }else
            {
                loginView.setActionTargetText("LogIn successful");
                User user = authenticationService.login(username,password).getResult();
               switch (user.getStringRoles())
               {

                   case ADMINISTRATOR -> AdminComponentFactory.getInstance(componentsForTest,loginView.getStage());
                   case CUSTOMER -> EmployeeComponentFactory.getInstance(componentsForTest,loginView.getStage(),user);
                   case EMPLOYEE -> EmployeeComponentFactory.getInstance(componentsForTest,loginView.getStage(),user) ;

               }

            }

        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();


         Notification<Boolean> registerNotification = authenticationService.register(username,password,CUSTOMER);
            if(registerNotification.hasErrors())
            {
              loginView.setActionTargetText(registerNotification.getFormattedErrors());
            }
            else {
                loginView.setActionTargetText("Register successful!");

            }


        }
    }
}
