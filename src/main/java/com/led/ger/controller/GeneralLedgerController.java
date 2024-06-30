package com.led.ger.controller;

import com.led.ger.entity.GeneralLedger;
import com.led.ger.repository.GeneralLedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/general-ledger")
public class GeneralLedgerController {

  @Autowired
  GeneralLedgerRepository generalLedgerRepository;
  @PostMapping("/create")
  @PreAuthorize("hasRole('ADMIN')")
  public GeneralLedger createGeneralLedger(@RequestBody GeneralLedger generalLedger) {
    return generalLedgerRepository.save(generalLedger);
  }
}
