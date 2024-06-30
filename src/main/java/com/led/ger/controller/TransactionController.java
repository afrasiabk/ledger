package com.led.ger.controller;

import com.led.ger.entity.Transaction;
import com.led.ger.payload.request.CreateTransactionRequest;
import com.led.ger.service.TransactionService;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/transaction")
public class TransactionController {

  @Autowired
  TransactionService transactionService;

  @Transactional
  @PostMapping("/create")
  @PreAuthorize("hasRole('ADMIN')")
  public Transaction getTransaction(@RequestBody CreateTransactionRequest request) {
    return transactionService.createTransaction(request);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public Transaction getTransaction(@PathVariable Long id) {
    return transactionService.getTransaction(id);
  }

  @GetMapping("/all")
  @PreAuthorize("hasRole('ADMIN')")
  public List<Transaction> getAllTransactions() {
    return transactionService.getAllTransactions();
  }

  @GetMapping("/all/sheet")
  @PreAuthorize("hasRole('ADMIN')")
  public void getAllTransactionsSheet(HttpServletResponse response) throws DocumentException, IOException {
    response.setContentType("application/pdf");
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    String currentDateTime = dateFormatter.format(new Date());

    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
    response.setHeader(headerKey, headerValue);

    transactionService.export(response);
  }

}
