package com.mindhub.App.Homebanking.services.Implement;

import com.mindhub.App.Homebanking.dtos.TransactionDTO;
import com.mindhub.App.Homebanking.models.Account;
import com.mindhub.App.Homebanking.models.Transaction;
import com.mindhub.App.Homebanking.repositories.TransactionRepository;
import com.mindhub.App.Homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImplement implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDTO> getTransactionsDTO() {
        return transactionRepository.findAll()
                .stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getTransactionsByDate(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findBetween(startDate, endDate);
    }

    @Override
    public byte[] generatePDF(Account account, List<Transaction> transactions) {

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);

            document.open();

            // Agregar datos de la cuenta
            document.add(new Paragraph("Account Number: " + account.getAccountNumber()));
            document.add(new Paragraph("Account Holder: " + account.getAccountHolder()));
            document.add(new Paragraph("Balance: " + account.getBalance()));

            // Agregar tabla de transacciones
            PdfPTable table = new PdfPTable(4);
            table.addCell("ID");
            table.addCell("Fecha");
            table.addCell("Descripción");
            table.addCell("Monto");

            for (Transaction transaction : transactions) {
                table.addCell(String.valueOf(transaction.getId()));
                table.addCell(transaction.getFecha().toString());
                table.addCell(transaction.getDescripcion());
                table.addCell(String.valueOf(transaction.getMonto()));
            }

            document.add(table);
            document.close();

            return outputStream.toByteArray();
        } catch (DocumentException | IOException e) {
            // Manejar errores
            e.printStackTrace();
        }

        return new byte[0];
    }
}
