package com.led.ger.repository;


import com.led.ger.entity.Account;
import com.led.ger.entity.JournalEntry;
import com.led.ger.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {

    BigDecimal findByAccount(Account assets);
}
