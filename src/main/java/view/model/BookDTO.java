package view.model;

import javafx.beans.property.*;

public class BookDTO {
    private StringProperty author = new SimpleStringProperty();
    private IntegerProperty stock = new SimpleIntegerProperty();
    private FloatProperty price = new SimpleFloatProperty();
    private StringProperty title = new SimpleStringProperty();
    //private IntegerProperty quantity = new SimpleIntegerProperty();


    public void setAuthor(String author) {
        authorProperty().set(author);
    }

    public String getAuthor() {
        return authorProperty().get();
    }

    public StringProperty authorProperty() {
        return author;
    }

    public void setTitle(String title) {
        titleProperty().set(title);
    }

    public String getTitle() {
        return titleProperty().get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setStock(int stock) {
        stockProperty().set(stock);
    }

    public int getStock() {
        return stockProperty().get();
    }

    public IntegerProperty stockProperty() {
        return stock;
    }


    public void setPrice(float price) {
        priceProperty().set(price);
    }

    public float getPrice() {
        return priceProperty().get();
    }

    public FloatProperty priceProperty() {
        return price;
    }

    /*public void setQuantity(int quantity)
    {
        quantityProperty().set(quantity);
    }
    public int getQuantity()
    {
        return quantityProperty().get();
    }
    public IntegerProperty quantityProperty()
    {
        return quantity;
    }*/
}
