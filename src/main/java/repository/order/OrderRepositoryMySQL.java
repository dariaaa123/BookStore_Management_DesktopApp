package repository.order;

import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryMySQL implements OrderRepository{

    private Connection connection;
    public OrderRepositoryMySQL(Connection connection){
        this.connection=connection;
    }
    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean create(Order order) {

        String newSql = "INSERT INTO orders VALUES (null,?, ?, ?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(newSql)) {
            preparedStatement.setTimestamp(1, order.getTimestamp());
            preparedStatement.setLong(2, order.getUserId());
            preparedStatement.setString(3, order.getBookTitle());
            preparedStatement.setString(4,order.getBookAuthor());
            preparedStatement.setFloat(5,order.getQuantity());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean delete(Order order) {
        return false;
    }

    @Override
    public void removeAll() {

    }
}
