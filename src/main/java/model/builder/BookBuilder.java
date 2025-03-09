package model.builder;

import model.Book;

import java.time.LocalDate;

public class BookBuilder {
    private Book book;

    public BookBuilder() {
        this.book = new Book();
    }

    public BookBuilder setStock(int stock)
    {
        book.setStock(stock);
        return this;
    }
    public BookBuilder setPrice(float price)
    {
        book.setPrice(price);
        return this;
    }
    public BookBuilder setId(Long id)
    {
        book.setId(id);
        return this;
    }
    public BookBuilder setAuthor(String author)
    {
        book.setAuthor(author);
        return this;
    }
    public BookBuilder setTitle(String title)
    {
        book.setTitle(title);
        return this;
    }
    public BookBuilder setPublishedDate(LocalDate date)
    {
        book.setPublishedDate(date);
        return this;
    }
    public Book build()
    {
        return book;
    }

}