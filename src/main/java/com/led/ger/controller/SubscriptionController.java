package com.led.ger.controller;

import com.led.ger.entity.Subscription;
import com.led.ger.repository.ClientRepository;
import com.led.ger.repository.PlanRepository;
import com.led.ger.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

  @Autowired
  SubscriptionRepository subscriptionRepository;

  @Autowired
  PlanRepository planRepository;

  @Autowired
  ClientRepository clientRepository;

  @PostMapping("/subscribe")
  @PreAuthorize("hasRole('ADMIN')")
  public Subscription subscribe(@RequestBody Subscription subscription) {
    System.out.println(subscriptionRepository.findActiveByUserId(1L).get());
    subscription.setStatus("A");
    subscription.setPlan(planRepository.findById(subscription.getPlan_id()).get());
    subscription.setClient(clientRepository.findById(subscription.getClient_id()).get());
    return subscriptionRepository.save(subscription);
  }
}
