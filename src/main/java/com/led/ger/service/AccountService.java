package com.led.ger.service;

import com.led.ger.entity.Account;
import com.led.ger.entity.Transaction;
import com.led.ger.entity.User;
import com.led.ger.enumeration.AccountType;
import com.led.ger.payload.request.CreateAccountRequest;
import com.led.ger.repository.AccountRepository;
import com.led.ger.repository.UserRepository;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.led.ger.security.jwt.AuthTokenFilter.loggedInUser;
import static com.led.ger.util.AccountingUtil.ACCOUNT_HEADS;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    protected void incrementBalance(Account account, BigDecimal amount){
        while(account.getParent()!=null){
            account.setBalance(account.getBalance().add(amount));
            accountRepository.save(account);
            account = account.getParent();
        }
        account.setBalance(account.getBalance().add(amount)); //for head node
        accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> getAllAccountHeads() {
        return accountRepository.findAllAccountHeads(loggedInUser.getClient().getGeneralLedger().getId());
    }

    public Account getAccount(Long id) {
        return accountRepository.findById(id).get();
    }

    public Account createAccount(CreateAccountRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userRepository.findById(userDetails.getId()).get();

        Account parentAccount = accountRepository.findById(request.getParentId()).get();
        parentAccount.setId(request.getParentId());

        Account account = Account.builder()
                .title(request.getTitle())
                .balance(BigDecimal.ZERO)
                .parent(parentAccount)
                .createdBy(user)
                .generalLedger(user.getClient().getGeneralLedger())
                .accountHead(parentAccount.getAccountHead())
                .build();

        return accountRepository.save(account);

    }

    public String createAccountBySheet(File file){
        try {
            List<Account> accounts = new ArrayList<>();

            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;
            nextRecord = csvReader.readNext();
            while ((nextRecord = csvReader.readNext()) != null) {
                List<String> row = Arrays.stream(nextRecord).toList();
                Account parent = accountRepository.findById(Long.parseLong(row.get(1))).get();
                Account head = null;
                if (ACCOUNT_HEADS.contains(parent.getTitle().toUpperCase())) {
                    if (parent.getTitle().equalsIgnoreCase(AccountType.ASSETS.name())){
                        head = accountRepository.findByTitleIgnoreCase(AccountType.ASSETS.name());
                    }
                    else if (parent.getTitle().equalsIgnoreCase(AccountType.LIABILITIES.name())){
                        head = accountRepository.findByTitleIgnoreCase(AccountType.LIABILITIES.name());
                    }
                    else if (parent.getTitle().equalsIgnoreCase(AccountType.CAPITAL.name())){
                        head = accountRepository.findByTitleIgnoreCase(AccountType.CAPITAL.name());
                    }
                }
                else {
                    head = parent.getAccountHead();
                }
                accounts.add(Account.builder()
                        .title(row.getFirst())
                        .createdBy(loggedInUser)
                        .accountHead(parent.getAccountHead())
                        .parent(parent)
                        .build());
            }
            accountRepository.saveAll(accounts);
            return "parsed"+accounts.size();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "parsed not";
        }
    }
}
