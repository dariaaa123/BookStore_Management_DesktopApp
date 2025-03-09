package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import model.Order;
import model.User;
import model.builder.OrderBuilder;
import service.book.BookService;
import service.order.OrderService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class BookController {
    private final BookView bookView;
    private final BookService bookService;
    private final OrderService orderService;
    private final User user;

    public BookController(BookView bookView, BookService bookService,OrderService orderService,User user)
    {
        this.user = user;
        this.bookView = bookView;
        this.bookService = bookService;
        this.orderService = orderService;
        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
        this.bookView.addSellButtonListener(new SellButtonListener());
    }
    private class SaveButtonListener implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();
            int stock = bookView.getStock();
            float price = bookView.getPrice();


            if (title.isEmpty() || author.isEmpty())
            {
                bookView.addDisplayAlertMessage("Save Error", "Problem at Author or Title fields","Can not have an empty Title or Author fields.");
            }else
            {
                BookDTO bookDTO = new BookDTOBuilder().setTitle(title).setAuthor(author).setStock(stock).
                setPrice(price).build();

                boolean savedBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));

                if(savedBook)
                {
                    bookView.addDisplayAlertMessage("Successful", "Book added","Book was successfully added to the database");
                    bookView.addBookToObservableList(bookDTO);
                }
                else
                {
                    bookView.addDisplayAlertMessage("Save Error", "Problem at adding book","There was a problem at adding the book to the database. Please try again.");

                }

            }

        }
    }
    private class DeleteButtonListener implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if(bookDTO != null)
            {
                boolean deletionSuccessful = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                if (deletionSuccessful)
                {
                    bookView.addDisplayAlertMessage("Successful", "Book deleted","Book was successfully deleted from the database");
                    bookView.removeBookFromObservableList(bookDTO);
                }else
                {
                    bookView.addDisplayAlertMessage("Delete Error", "Problem at deleting book","There was a problem with the database. Please try again.");

                }
             }else
            {
                bookView.addDisplayAlertMessage("Delete Error", "Problem at deleting book","You must select a book before pressing the delete button.");

            }
        }
    }
    private class SellButtonListener implements EventHandler<ActionEvent>
    {

        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            BookDTO oldBookDTO = bookDTO;
            if(bookDTO != null)
            {
                boolean updateSuccessful = bookService.update(BookMapper.convertBookDTOToBook(bookDTO),BookMapper.convertBookDTOToBook(bookDTO).getStock()-bookView.getQuantity());
                if (updateSuccessful)
                {
                    Order order = new OrderBuilder().setId(null).setBookTitle(bookDTO.getTitle()).setBookAuthor(bookDTO.getAuthor()).setTimestamp(Timestamp.from(Instant.now()))
                            .setQuantity(bookView.getQuantity()).setUserId(user.getId()).build();
                    orderService.save(order);
                    bookView.addDisplayAlertMessage("Successful", "Created order","The order was successfully made!");

                }else
                {
                    bookView.addDisplayAlertMessage("Order Error", "Problem at ordering","There was a problem with the order. Please try again.");
                }
            }else
            {
                bookView.addDisplayAlertMessage("Order Error", "Problem at updating book","");

            }

        }
    }

}
