package com.led.ger.service;

import com.led.ger.entity.Account;
import com.led.ger.entity.Transaction;
import com.led.ger.entity.User;
import com.led.ger.enumeration.JournalEntryType;
import com.led.ger.payload.request.CreateTransactionRequest;
import com.led.ger.repository.*;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.led.ger.security.jwt.AuthTokenFilter.loggedInUser;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GeneralLedgerRepository generalLedgerRepository;

    @Autowired
    JournalEntryService journalEntryService;

    @Transactional
    public Transaction createTransaction(CreateTransactionRequest request){

        Transaction transaction = Transaction.builder()
                .amount(new BigDecimal(request.getAmount()).setScale(5,RoundingMode.HALF_UP))
                .type(request.getTransactionType())
                .createdBy(loggedInUser)
                .generalLedger(loggedInUser.getClient().getGeneralLedger())
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        //double entry - simple
        journalEntryService.createJournalEntry(request.getDebitAccountId(), JournalEntryType.DEBIT, savedTransaction);
        journalEntryService.createJournalEntry(request.getCreditAccountId(), JournalEntryType.CREDIT, savedTransaction);

        return savedTransaction;
    }


    public BigDecimal getBalance(Account assets) {

        journalEntryService.getBalance(assets);
        //transactionRepository.findByAccount(assets);
        return null;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransaction(Long id) {
        return transactionRepository.findById(id).get();
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of Transactions", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {3f, 3f, 3f, 3f, 3f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Transaction ID", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Amount", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Type", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Created By", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("General Ledger Id", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Transaction transaction : transactionRepository.findAll()) {
            table.addCell(String.valueOf(transaction.getId()));
            table.addCell(transaction.getAmount().setScale(2,RoundingMode.HALF_UP).toString());
            table.addCell(transaction.getType());
            table.addCell(transaction.getCreatedBy().getUsername());
            table.addCell(String.valueOf(transaction.getGeneralLedger().getId()));
        }
    }

}
