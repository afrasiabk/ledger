package com.led.ger.service;

import com.led.ger.entity.Account;
import com.led.ger.entity.JournalEntry;
import com.led.ger.entity.Transaction;
import com.led.ger.enumeration.AccountType;
import com.led.ger.enumeration.JournalEntryType;
import com.led.ger.payload.request.CreateTransactionRequest;
import com.led.ger.repository.AccountRepository;
import com.led.ger.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class JournalEntryService {

    @Autowired
    JournalEntryRepository journalEntryRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Transactional
    public void createJournalEntry(Long accountId, JournalEntryType journalEntryType, Transaction savedTransaction){

        Account account = accountRepository.findById(accountId).get();

        JournalEntry journalEntry = JournalEntry.builder()
                .account(account)
                .transaction(savedTransaction)
                .journalEntryType(journalEntryType.name())
                .build();

        journalEntryRepository.save(journalEntry);

        accountService.incrementBalance(account, savedTransaction.getAmount());

    }

    public BigDecimal getBalance(Account assets) {
        return journalEntryRepository.findByAccount(assets);
    }
}
