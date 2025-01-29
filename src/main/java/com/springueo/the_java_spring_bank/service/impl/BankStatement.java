package com.springueo.the_java_spring_bank.service.impl;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.springueo.the_java_spring_bank.DTO.EmailDetails;
import com.springueo.the_java_spring_bank.entity.Transaction;
import com.springueo.the_java_spring_bank.entity.User;
import com.springueo.the_java_spring_bank.repository.TransactionRepository;
import com.springueo.the_java_spring_bank.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BankStatement {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private EmailService emailService;
    private static final String FILE_PATH = "C:\\Users\\BROLLY\\Documents\\Bank_pdf\\TransactionsList.pdf";

    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

        User user = userRepository.findByAccountNumber(accountNumber);

        List<Transaction> transactions = transactionRepository.findByAccountNumberAndCreatedAtBetween(
                accountNumber,
                start,
                end
        );


        try {
            generatePdfStatement(transactions, user, startDate, endDate);
        } catch (Exception e) {
            log.error("Failed to generate PDF statement", e);
        }

        return transactions;
    }

    private void generatePdfStatement(List<Transaction> transactions, User user,
                                      String startDate, String endDate)
            throws Exception {

        // Create directory if it doesn't exist
//        Path dirPath = Paths.get(System.getProperty("C:\\Users\\BROLLY\\Documents\\Bank_pdf"), "TransactionsList.pdf");
//        Files.createDirectories(dirPath);

        String fileName = String.format(FILE_PATH, user.getAccountNumber());
        Document document = new Document(PageSize.A4);
        log.info("setting size of document");
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        String customerName = String.format("%s %s %s",
                user.getFirstName(),
                user.getLastName(),
                user.getOtherName()
        );

        try {
            PdfPTable bankInfoTable = new PdfPTable(1);
            PdfPCell bankName = new PdfPCell(new Phrase("The Java Academy Bank"));
            bankName.setBorder(0);
            bankName.setBackgroundColor(BaseColor.MAGENTA);
            bankName.setPadding(20f);

            PdfPCell bankAddress = new PdfPCell(new Phrase("123 Maplewood Crescent, Riverview Heights, MO 65432, USA"));
            bankAddress.setBorder(0);
            bankInfoTable.addCell(bankName);
            bankInfoTable.addCell(bankAddress);

            PdfPTable statementInfo = new PdfPTable(2);
            PdfPCell customerInfo = new PdfPCell(new Phrase("Start Date: " + startDate));
            customerInfo.setBorder(0);
            PdfPCell statement = new PdfPCell(new Phrase("STATEMENT OF ACCOUNT"));
            statement.setBorder(0);
            PdfPCell stopDate = new PdfPCell(new Phrase("End Date: " + endDate));
            stopDate.setBorder(0);

            PdfPCell name = new PdfPCell(new Phrase("Customer Name: " + customerName));
            name.setBorder(0);
            PdfPCell space = new PdfPCell();
            space.setBorder(0);
            PdfPCell address = new PdfPCell(new Phrase("Customer Address: " + user.getAddress()));
            address.setBorder(0);

            PdfPTable transactionsTable = new PdfPTable(4);
            PdfPCell date = new PdfPCell(new Phrase("DATE"));
            date.setBackgroundColor(BaseColor.BLUE);
            date.setBorder(0);
            PdfPCell transactionType = new PdfPCell(new Phrase("TRANSACTION TYPE"));
            transactionType.setBackgroundColor(BaseColor.BLUE);
            transactionType.setBorder(0);
            PdfPCell transactionAmount = new PdfPCell(new Phrase("TRANSACTION AMOUNT"));
            transactionAmount.setBackgroundColor(BaseColor.BLUE);
            transactionAmount.setBorder(0);
            PdfPCell status = new PdfPCell(new Phrase("STATUS"));
            status.setBackgroundColor(BaseColor.BLUE);
            status.setBorder(0);

            transactionsTable.addCell(date);
            transactionsTable.addCell(transactionType);
            transactionsTable.addCell(transactionAmount);
            transactionsTable.addCell(status);

            // Add transaction data
            for (Transaction transaction : transactions) {
                transactionsTable.addCell(new PdfPCell(new Phrase(transaction.getCreatedAt().toString())));
                transactionsTable.addCell(new PdfPCell(new Phrase(transaction.getTransactionType())));
                transactionsTable.addCell(new PdfPCell(new Phrase(transaction.getAmount().toString())));
                transactionsTable.addCell(new PdfPCell(new Phrase(transaction.getStatus())));
            }

//            transactions.forEach(transaction -> {
//                transactionsTable.addCell(new PdfPCell(new Phrase(transaction.getCreatedAt().toString())));
//                transactionsTable.addCell(new PdfPCell(new Phrase(transaction.getTransactionType())));
//                transactionsTable.addCell(new PdfPCell(new Phrase(transaction.getAmount().toString())));
//                transactionsTable.addCell(new PdfPCell(new Phrase(transaction.getStatus())));
//            });

            statementInfo.addCell(customerInfo);
            statementInfo.addCell(statement);
            statementInfo.addCell(stopDate);
            statementInfo.addCell(name);
            statementInfo.addCell(space);
            statementInfo.addCell(address);

            document.add(bankInfoTable);
            document.add(statementInfo);
            document.add(transactionsTable);

            EmailDetails emailDetails = EmailDetails.builder()
                    .recipient(user.getEmail())
                    .subject("STATEMENT OF ACCOUNT")
                    .messageBody("Kindly find your requested account statement attached!")
                    .attchment(FILE_PATH)
                    .build();

            emailService.sendEmailWithAttachment(emailDetails);

        } finally {
            document.close();
        }
    }
}