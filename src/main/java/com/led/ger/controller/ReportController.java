package com.led.ger.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.led.ger.entity.Account;
import com.led.ger.entity.GeneralLedger;
import com.led.ger.enumeration.AccountType;
import com.led.ger.repository.AccountRepository;
import com.led.ger.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/accounts")
    @PreAuthorize("hasRole('ADMIN')")
    public HashMap<String, Account> getAccounts() {
        return reportService.nestedAccounts();
    }
    @Transactional
    protected void checkBalance() {
        List<Account> accounts = accountRepository.findAll();
        List<Account> assets = accountRepository.findAll();
        List<Account> liabilities = accountRepository.findAll();
        List<Account> capital = accountRepository.findAll();

        accounts.forEach(a->{
            Account accountHead = a;
            while (accountHead.getParent()!= null){
                accountHead = a.getParent();
            }
            if (a.getTitle().equalsIgnoreCase(AccountType.ASSETS.name())){
                assets.add(a);
            }
            else if (a.getTitle().equalsIgnoreCase(AccountType.LIABILITIES.name())){
                liabilities.add(a);
            }
            else if (a.getTitle().equalsIgnoreCase(AccountType.CAPITAL.name())){
                capital.add(a);
            }
        });

        System.out.println("Assets");
        assets.forEach(a->{
            System.out.println(a.getTitle());
        });

        System.out.println("Liabilities");
        liabilities.forEach(a->{
            System.out.println(a.getTitle());
        });

        System.out.println("Capital");
        capital.forEach(a->{
            System.out.println(a.getTitle());
        });
    }

}
