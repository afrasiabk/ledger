package com.led.ger.repository;


import com.led.ger.entity.GeneralLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralLedgerRepository extends JpaRepository<GeneralLedger, Long> {

}
