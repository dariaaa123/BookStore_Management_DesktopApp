package repository.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileOutputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PDFGenerateMySQLRepo implements PDFGenerateRepo {
    private Connection connection;
    private static final String OUTPUT_FILE = "user_orders_report.pdf";

    public PDFGenerateMySQLRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Map<String, Map<String, Double>> fetchUserOrders() {
        Map<String, Map<String, Double>> userEarnings = new HashMap<>();
        String query = "SELECT \n" +
                "    u.username AS user_name,\n" +
                "    DATE_FORMAT(o.time_stamp, '%Y-%m') AS order_month,\n" +
                "    SUM(o.quantity * b.price) AS total_earnings\n" +
                "FROM \n" +
                "    orders o\n" +
                "JOIN \n" +
                "    user u ON o.user_id = u.id\n" +

                "JOIN \n" +
                "    book b ON o.book_title = b.title\n" +
                "GROUP BY \n" +
                "    u.username, DATE_FORMAT(o.time_stamp, '%Y-%m')\n" +
                "ORDER BY \n" +
                "    u.username, order_month;\n";

        try (Statement stmt = connection.createStatement();
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
    public void generateUserOrdersReport(Map<String, Map<String, Double>> userEarnings) {
        try {

            PdfWriter writer = new PdfWriter(new FileOutputStream(OUTPUT_FILE));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);


            document.add(new Paragraph("User Orders and Earnings Report")
                    .setBold()
                    .setFontSize(18));


            float[] columnWidths = {200f, 150f, 150f};
            Table table = new Table(columnWidths);
            table.addCell(new Cell().add(new Paragraph("User")));
            table.addCell(new Cell().add(new Paragraph("Month")));
            table.addCell(new Cell().add(new Paragraph("Earnings ($)")));


            for (String user : userEarnings.keySet()) {
                Map<String, Double> earningsByMonth = userEarnings.get(user);
                for (String month : earningsByMonth.keySet()) {
                    table.addCell(new Cell().add(new Paragraph(user)));
                    table.addCell(new Cell().add(new Paragraph(month)));
                    table.addCell(new Cell().add(new Paragraph(String.format("%.2f", earningsByMonth.get(month)))));
                }
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean generate() {
        try {
            Map<String, Map<String, Double>> userOrders = fetchUserOrders();
            generateUserOrdersReport(userOrders);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
