package repository.pdf;

import service.pdfGenerator.PDFGenerator;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PDFGenerateMySQLRepo implements PDFGenerateRepo {
    private Connection connection;

    public PDFGenerateMySQLRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Map<String, Map<String, Double>> fetchUserOrders() {
        Map<String, Map<String, Double>> userEarnings = new HashMap<>();
        String query = "SELECT u.username, DATE_FORMAT(o.time_stamp, '%Y-%m') AS month, " +
                "SUM(o.quantity * b.price) AS earnings " +
                "FROM orders o " +
                "JOIN user u ON o.user_id = u.id " +
                "JOIN book b ON b.title = o.book_title " +
                "GROUP BY u.username, DATE_FORMAT(o.time_stamp, '%Y-%m')";

        try (
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String username = rs.getString("username");
                String month = rs.getString("month");
                double earnings = rs.getDouble("earnings");

                userEarnings.putIfAbsent(username, new HashMap<>());
                userEarnings.get(username).put(month, earnings);
                System.out.println("User: " + username + ", Month: " + month + ", Earnings: " + earnings);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userEarnings;
    }


    @Override
    public boolean generate() {
        // Fetch user orders data
        Map<String, Map<String, Double>> userOrders = fetchUserOrders();

        // Generate the PDF report using the fetched data
        PDFGenerator pdfGenerator = new PDFGenerator();
        pdfGenerator.generateUserOrdersReport(userOrders);
        return true;
    }
}
