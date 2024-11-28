package repository.book;

import model.Book;
import model.builder.BookBuilder;
import repository.book.BookRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMySQL implements BookRepository {
    private Connection connection;
    public BookRepositoryMySQL(Connection connection){
        this.connection=connection;
    }
    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book";
        List<Book> books = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }


    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        Optional<Book> book = Optional.empty();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                book = Optional.of(getBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }


    @Override
    public boolean save(Book book) {
        String newSql = "INSERT INTO book (author, title, publishedDate,stock,price) VALUES (?, ?, ?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(newSql)) {
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            preparedStatement.setInt(4,book.getStock());
            preparedStatement.setFloat(5,book.getPrice());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean delete(Book book) {
        String newSql = "DELETE FROM book WHERE author = ? AND title = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(newSql)) {
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Book book,int newStockValue) {

        String newSql = "update book set stock = ? where title = ? and author = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(newSql)) {
            preparedStatement.setInt(1,newStockValue );
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void removeAll() {
        String sql = "DELETE FROM book";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException
    {
        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .setStock(resultSet.getInt("stock"))
                .setPrice(resultSet.getFloat("price"))
                .build();
    }
}
