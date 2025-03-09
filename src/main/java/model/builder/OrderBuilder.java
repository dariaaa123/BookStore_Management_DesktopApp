package model.builder;

import model.Order;

import java.sql.Timestamp;

public class OrderBuilder {
    private Long id;
    private Timestamp timestamp;
    private Long userId;
    private String bookTitle;
    private String bookAuthor;
    private Integer quantity;

    public OrderBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public OrderBuilder setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public OrderBuilder setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public OrderBuilder setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
        return this;
    }

    public OrderBuilder setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
        return this;
    }

    public OrderBuilder setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Order build() {
        Order order = new Order();
        order.setId(this.id);
        order.setTimestamp(this.timestamp);
        order.setUserId(this.userId);
        order.setBookTitle(this.bookTitle);
        order.setBookAuthor(this.bookAuthor);
        order.setQuantity(this.quantity);
        return order;
    }
}
