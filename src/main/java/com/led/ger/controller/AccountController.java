package com.led.ger.controller;

import com.led.ger.entity.Account;
import com.led.ger.entity.Transaction;
import com.led.ger.payload.request.CreateAccountRequest;
import com.led.ger.payload.request.CreateTransactionRequest;
import com.led.ger.service.AccountService;
import com.led.ger.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/account")
public class AccountController {

  @Autowired
  AccountService accountService;

  @Transactional
  @PostMapping("/create")
  @PreAuthorize("hasRole('ADMIN')")
  public Account createAccount(@RequestBody CreateAccountRequest request) {
    return accountService.createAccount(request);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public Account getAccount(@PathVariable Long id) {
    return accountService.getAccount(id);
  }

  @GetMapping("/all")
  @PreAuthorize("hasRole('ADMIN')")
  public List<Account> getAllAccounts() {
    return accountService.getAllAccounts();
  }

  @GetMapping("/all/heads")
  @PreAuthorize("hasRole('ADMIN')")
  public List<Account> getAllAccountHeads() {
    return accountService.getAllAccountHeads();
  }

  @PostMapping("/create/sheet")
  @PreAuthorize("hasRole('ADMIN')")
  public String createAccountsBySheet(@RequestParam("file") MultipartFile file) throws IOException {
    File convFile = new File( file.getOriginalFilename() );
    FileOutputStream fos = new FileOutputStream( convFile );
    fos.write( file.getBytes() );
    fos.close();
    return accountService.createAccountBySheet(convFile);
  }


}
