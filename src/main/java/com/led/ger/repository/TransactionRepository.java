package com.led.ger.repository;


import com.led.ger.entity.Account;
import com.led.ger.entity.GeneralLedger;
import com.led.ger.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
