package service.pdfGenerator;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class PDFGenerator {
    private static final String OUTPUT_FILE = "user_orders_report.pdf";

    public void generateUserOrdersReport(Map<String, Map<String, Double>> userEarnings) {
        try {
            // Initialize PDF writer and document
            PdfWriter writer = new PdfWriter(new FileOutputStream(OUTPUT_FILE));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Add Title
            document.add(new Paragraph("User Orders and Earnings Report")
                    .setBold()
                    .setFontSize(18));

            // Create a table for the data
            float[] columnWidths = {200f, 150f, 150f};
            Table table = new Table(columnWidths);
            table.addCell(new Cell().add(new Paragraph("User")));
            table.addCell(new Cell().add(new Paragraph("Month")));
            table.addCell(new Cell().add(new Paragraph("Earnings ($)")));

            // Populate the table with data
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

            System.out.println("PDF generated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
