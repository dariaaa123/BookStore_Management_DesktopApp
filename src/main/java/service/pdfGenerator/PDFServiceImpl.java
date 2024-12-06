package service.pdfGenerator;

import repository.pdf.PDFGenerateRepo;

import java.sql.Connection;

public class PDFServiceImpl implements PDFService {
    private PDFGenerateRepo generatePDF;
    private Connection connection;

    public PDFServiceImpl(PDFGenerateRepo generatePDF, Connection connection) {
        this.generatePDF = generatePDF;
        this.connection = connection;
    }

    @Override
    public boolean generatePDF() {
        return generatePDF.generate();  // This will trigger the PDF generation
    }
}
