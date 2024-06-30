package com.led.ger.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.led.ger.entity.Account;
import com.led.ger.enumeration.AccountType;
import com.led.ger.repository.AccountRepository;
import com.led.ger.repository.JournalEntryRepository;
import com.led.ger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    TransactionService transactionService;

    @Autowired
    AccountRepository accountRepository;

    public HashMap<String, Account> nestedAccounts() {
        List<Account> accounts = accountRepository.findAll();

        Account assets = accountRepository.findById(1L).get();
        assets.setBalance(transactionService.getBalance(assets));

        Account liabilities = accountRepository.findById(1L).get();
        assets.setBalance(transactionService.getBalance(assets));

        Account capital = accountRepository.findById(1L).get();
        assets.setBalance(transactionService.getBalance(assets));

        HashMap<String, Account> hashMap = new HashMap<>();
        hashMap.put(AccountType.ASSETS.name(),accountRepository.findById(1L).get());
        hashMap.put(AccountType.LIABILITIES.name(),accountRepository.findById(2L).get());
        hashMap.put(AccountType.CAPITAL.name(),accountRepository.findById(3L).get());

        return hashMap;
    }
}
