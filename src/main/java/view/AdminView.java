package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.User;

import java.util.List;

public class AdminView {
    private final ObservableList<User> usersObservableList;
    private TableView<User> usersTableView;
    private TextField usernameTextField;
    private TextField passwordTextField;
    private TextField roleTextField;

    private Label usernameLabel;
    private Label passwordLabel;
    private Label roleLabel;

    private Button saveButton;
    private Button deleteButton;
    private Button generateReportButton;

    public AdminView(Stage primaryStage, List<User> users) {
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPage(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        usersObservableList = FXCollections.observableArrayList(users);
        initTableView(gridPane);

        initSaveOptions(gridPane);
        primaryStage.show();
    }

    private void initializeGridPage(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initTableView(GridPane gridPane) {
        usersTableView = new TableView<>();

        usersTableView.setPlaceholder(new Label("No users to display"));


        TableColumn<User, String> usernameColumn = new TableColumn<User,String>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> roleColumn = new TableColumn<User, String>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("stringRoles"));


        usersTableView.getColumns().addAll(usernameColumn,roleColumn);

        usersTableView.setItems(usersObservableList);

        gridPane.add(usersTableView, 0, 0, 5, 1);
    }

    private void initSaveOptions(GridPane gridPane) {
        usernameLabel = new Label("Username");
        gridPane.add(usernameLabel, 1, 1);

        usernameTextField = new TextField();
        gridPane.add(usernameTextField, 2, 1);

        passwordLabel = new Label("Password");
        gridPane.add(passwordLabel, 1, 2);

        passwordTextField = new PasswordField();
        gridPane.add(passwordTextField, 2, 2);

        roleLabel = new Label("Role");
        gridPane.add(roleLabel, 3, 1);

        roleTextField = new TextField();
        gridPane.add(roleTextField, 4, 1);


        saveButton = new Button("Save");
        gridPane.add(saveButton, 5, 3);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 6, 3);

        generateReportButton = new Button("Generate PDF report");
        gridPane.add(generateReportButton, 7, 3);
    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener) {
        saveButton.setOnAction(saveButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener) {
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addGenerateButtonListener(EventHandler<ActionEvent> generateButtonListener) {
        generateReportButton.setOnAction(generateButtonListener);
    }

    public void addDisplayAlertMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public String getUsername()
    {
        return usernameTextField.getText();
    }
    public String getPassword()
    {
        return passwordTextField.getText();
    }
    public String getRole()
    {
        return roleTextField.getText();
    }
    public void addUserToObservableList(User user) {
        this.usersObservableList.add(user);
    }

    public TableView<User> getUsersTableView() {
        return usersTableView;
    }

    public void setUsersTableView(TableView<User> usersTableView) {
        this.usersTableView = usersTableView;
    }

    public ObservableList<User> getUsersObservableList() {
        return usersObservableList;
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public void setUsernameTextField(TextField usernameTextField) {
        this.usernameTextField = usernameTextField;
    }

    public TextField getPasswordTextField() {
        return passwordTextField;
    }

    public void setPasswordTextField(TextField passwordTextField) {
        this.passwordTextField = passwordTextField;
    }

    public String getRoleTextField() {
        return String.valueOf(roleTextField);
    }

    public void setRoleTextField(TextField roleTextField) {
        this.roleTextField = roleTextField;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public void setUsernameLabel(Label usernameLabel) {
        this.usernameLabel = usernameLabel;
    }

    public Label getPasswordLabel() {
        return passwordLabel;
    }

    public void setPasswordLabel(Label passwordLabel) {
        this.passwordLabel = passwordLabel;
    }

    public Label getRoleLabel() {
        return roleLabel;
    }

    public void setRoleLabel(Label roleLabel) {
        this.roleLabel = roleLabel;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(Button saveButton) {
        this.saveButton = saveButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

    public Button getGenerateReportButton() {
        return generateReportButton;
    }

    public void setGenerateReportButton(Button generateReportButton) {
        this.generateReportButton = generateReportButton;
    }


}
