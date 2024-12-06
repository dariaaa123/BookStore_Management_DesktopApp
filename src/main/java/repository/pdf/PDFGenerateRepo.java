package repository.pdf;

import java.util.Map;

public interface PDFGenerateRepo {
     Map<String, Map<String, Double>> fetchUserOrders();
     boolean generate();
}
