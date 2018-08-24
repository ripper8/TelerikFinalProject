package com.telerik.payment_system.controllers;

import com.telerik.payment_system.entities.Bill;
import com.telerik.payment_system.entities.Service;
import com.telerik.payment_system.entities.Subscriber;
import com.telerik.payment_system.services.base.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {

    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }


    @GetMapping("/{phoneNumber}/{bankId}")
    public List<Bill> getAllNonePaymentBillsForSubscriber(
            @PathVariable("phoneNumber") String phoneNumber,
            @PathVariable("bankId") String bankId) {
        return bankService.getAllNonPaymentBillsForSubscriber(bankId, phoneNumber);
    }

    @GetMapping("/subscribers/details/{phoneNumber}")
    public Subscriber getSubscriberDetails(@PathVariable("phoneNumber") String phoneNumber) {
        return bankService.findByPhoneNumber(phoneNumber);
    }

    @GetMapping("/subscribers/history/{phoneNumber}")
    public List<Bill> getHistoryBySubscriber(@PathVariable("phoneNumber") String phoneNumber) {
        return bankService.getHistoryBySubscriber(phoneNumber);
    }

    @GetMapping("/subscribers/average/{phoneNumber}")
    public Double averageAmount(@PathVariable("phoneNumber") String phoneNumber) {
        return bankService.averageAmount(phoneNumber);
    }

    @GetMapping("/subscribers/max/{phoneNumber}")
    public Double maxAmount(@PathVariable("phoneNumber") String phoneNumber) {
        return bankService.maxAmount(phoneNumber);
    }

    @PostMapping("subscribers/pay/{phoneNumber}")
    public void payAllBillsBySubscriber(
            @PathVariable("phoneNumber") String phoneNumber,
            @PathVariable("bankId") String bankId) {
        bankService.payAllBillsBySubscriber(bankId, phoneNumber);
    }

    @GetMapping("subscribers/service/{phoneNumber}")
    public List<Service> getAllServices(@PathVariable("phoneNumber") String phoneNumber) {
        return bankService.getAllServices(phoneNumber);
    }

    @GetMapping("subscribers/top10")
    public HashMap<Subscriber, Double> findTop10() {
        return bankService.findTop10();
    }

}