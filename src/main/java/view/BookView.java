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
import view.model.BookDTO;

import java.util.List;

public class BookView {
    private TableView<BookDTO> bookTableView;
    private final ObservableList<BookDTO> booksObservableList;
    private TextField authorTextField;
    private TextField titleTextField;
    private TextField stockTextField;
    private TextField priceTextField;
    private TextField quantityTextField;
    private Label authorLabel;
    private Label titleLabel;
    private Label stockLabel;
    private Label priceLabel;
    private Label quantityLabel;
    private Button saveButton;
    private Button deleteButton;
    private Button sellButton;

    public BookView(Stage primaryStage, List<BookDTO> books) {
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPage(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        booksObservableList = FXCollections.observableArrayList(books);
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
        bookTableView = new TableView<>();

        bookTableView.setPlaceholder(new Label("No books to display"));

        TableColumn<BookDTO, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BookDTO, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<BookDTO, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<BookDTO, Float> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

       /* TableColumn<BookDTO, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));*/


        bookTableView.getColumns().addAll(titleColumn, authorColumn, stockColumn, priceColumn);

        bookTableView.setItems(booksObservableList);

        gridPane.add(bookTableView, 0, 0, 5, 1);
    }

    private void initSaveOptions(GridPane gridPane) {
        titleLabel = new Label("Title");
        gridPane.add(titleLabel, 1, 1);

        titleTextField = new TextField();
        gridPane.add(titleTextField, 2, 1);

        authorLabel = new Label("Author");
        gridPane.add(authorLabel, 1, 2);

        authorTextField = new TextField();
        gridPane.add(authorTextField, 2, 2);

        stockLabel = new Label("Stock");
        gridPane.add(stockLabel, 3, 1);

        stockTextField = new TextField();
        gridPane.add(stockTextField, 4, 1);

        priceLabel = new Label("Price");
        gridPane.add(priceLabel, 3, 2);

        priceTextField = new TextField();
        gridPane.add(priceTextField, 4, 2);

        quantityLabel = new Label("Quantity");
        gridPane.add(quantityLabel, 3, 3);

        quantityTextField = new TextField();
        gridPane.add(quantityTextField, 4, 3);

        saveButton = new Button("Save");
        gridPane.add(saveButton, 5, 3);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 6, 3);

        sellButton = new Button("Sell");
        gridPane.add(sellButton,7,3);
    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener) {
        saveButton.setOnAction(saveButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener) {
        deleteButton.setOnAction(deleteButtonListener);
    }
    public void addSellButtonListener(EventHandler<ActionEvent> sellButtonListener)
    {
        sellButton.setOnAction(sellButtonListener);
    }

    public void addDisplayAlertMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public String getTitle() {
        return titleTextField.getText();
    }

    public String getAuthor() {
        return authorTextField.getText();
    }

    public int getStock() {
        return Integer.parseInt(stockTextField.getText());
    }

    public float getPrice() {
        return Float.parseFloat(priceTextField.getText());
    }
    public int getQuantity()
    {
        return Integer.parseInt(quantityTextField.getText());
    }

    public void addBookToObservableList(BookDTO bookDTO) {
        this.booksObservableList.add(bookDTO);
    }

    public void removeBookFromObservableList(BookDTO bookDTO) {
        this.booksObservableList.remove(bookDTO);
    }

    public TableView<BookDTO> getBookTableView() {
        return bookTableView;
    }
}
