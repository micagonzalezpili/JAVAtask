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
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<Transaction> getTransactionsByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public byte[] generatePDF(Account account) {

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            List<Transaction> transactions1 = new ArrayList<>(account.getTransactions());

            document.open();

            // Agrego datos de la cuenta
            document.add(new Paragraph("Account Number: " + account.getNumber()));
            document.add(new Paragraph("Account Holder: " + account.getClient().getFirstName() + " " + account.getClient().getLastName()));
            document.add(new Paragraph("Balance: " + account.getBalance()));
            document.add(new Paragraph("Account Type: " + account.getAccountType()));

            // Agregar tabla de transacciones
            PdfPTable table = new PdfPTable(5);
            table.addCell("Type");
            table.addCell("Date");
            table.addCell("Description");
            table.addCell("Amount");
            table.addCell("Current Balance");

            for (Transaction transaction : transactions1) {
                table.addCell(String.valueOf(transaction.getType()));
                table.addCell(transaction.getDate().toString());
                table.addCell(transaction.getDescription());
                table.addCell(String.valueOf(transaction.getAmount()));
                table.addCell(String.valueOf(transaction.getBalance()));
            }

            document.add(table);
            document.close();

            return outputStream.toByteArray();
        } catch (DocumentException | IOException e) {
            // Manejo dee errores
            e.printStackTrace();
        }

        return new byte[0];
    }
}
