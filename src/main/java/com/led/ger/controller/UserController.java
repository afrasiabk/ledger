package com.led.ger.controller;

import com.led.ger.entity.GeneralLedger;
import com.led.ger.entity.User;
import com.led.ger.repository.GeneralLedgerRepository;
import com.led.ger.service.UserService;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  UserService userService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/all/pdf")
  public void allTransactionsPdf(HttpServletResponse response) throws DocumentException, IOException, IOException {
    response.setContentType("application/pdf");
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    String currentDateTime = dateFormatter.format(new Date());

    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
    response.setHeader(headerKey, headerValue);

    userService.export(response);

  }
}
