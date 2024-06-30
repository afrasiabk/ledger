package com.led.ger.repository;

import com.led.ger.entity.Account;
import com.led.ger.entity.GeneralLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByGeneralLedger(GeneralLedger generalLedger);

    @Query(value = "select * from account where parent_id is null and general_ledger_id = :generalLedgerId", nativeQuery = true)
    List<Account> findAllAccountHeads(Long generalLedgerId);

    Account findByTitleIgnoreCase(String title);

}
