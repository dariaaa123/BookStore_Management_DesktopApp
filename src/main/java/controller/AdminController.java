package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validator.Notification;
import service.admin.AdminService;
import service.pdfGenerator.PDFService;
import service.user.AuthentificationService;
import view.AdminView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

public class AdminController {
    private final AdminView adminView;
    private final AdminService adminService;
    private final AuthentificationService authentificationService;
    private final PDFService pdfService;

    public AdminController(AdminView adminView, AdminService adminService, AuthentificationService authentificationService,PDFService pdfService) {
        this.adminView = adminView;
        this.adminService = adminService;
        this.authentificationService = authentificationService;
        this.pdfService = pdfService;

        this.adminView.addSaveButtonListener(new AdminController.SaveButtonListener());
        this.adminView.addDeleteButtonListener(new AdminController.DeleteButtonListener());
        this.adminView.addSellButtonListener(new AdminController.GenerateReportButtonListener());
    }

    public class SaveButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();
            String stringRole = adminView.getRole();


            if (username.isEmpty() || password.isEmpty() || stringRole.isEmpty()) {
                adminView.addDisplayAlertMessage("Save Error", "Problem at Username or Password or Role fields", "Can not have an empty fields.");
            } else {
                Notification<Boolean> register = authentificationService.register(username, password, stringRole);
                User user = new UserBuilder()
                        .setUsername(username)
                        .setStringRoles(stringRole)
                        .build();

                if (register.hasErrors()) {
                    adminView.addDisplayAlertMessage("Save Error","Problem at adding user",register.getFormattedErrors());
                } else {
                    if (register.getResult()) {
                        adminView.addDisplayAlertMessage("Successful", "User added", "User was successfully added to the database");
                        adminView.addUserToObservableList(user);
                    } else {
                        adminView.addDisplayAlertMessage("Save Error", "Problem at adding user", "There was a problem at adding the user to the database. Please try again.");

                    }
                }


            }
        }
    }

    public class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

        }
    }

    public class GenerateReportButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (pdfService.generatePDF()) {
                adminView.addDisplayAlertMessage("Successful", "Generated pdf","");
            } else {
                adminView.addDisplayAlertMessage("Error", "Error at generating pdf","");
            }

        }
    }
}
