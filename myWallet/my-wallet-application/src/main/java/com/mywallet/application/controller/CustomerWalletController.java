package com.mywallet.application.controller;

import com.mywallet.application.dto.AccessTokenResponse;
import com.mywallet.application.dto.CustomerCredentials;
import com.mywallet.application.model.BalanceHistory;
import com.mywallet.application.model.Customer;
import com.mywallet.application.model.CustomerWallet;
import com.mywallet.application.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class CustomerWalletController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World";
    }

    @PostMapping("/customer")
    public Customer customer(@Valid @RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @PostMapping("/oauthTokens")
    public AccessTokenResponse oauthToken(@RequestBody CustomerCredentials customerCredentials) throws IOException {
        return customerService.generateAccessToken(customerCredentials);
    }

    @PostMapping("/credit")
    public CustomerWallet credit(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody CustomerWallet customerWallet) {
        customerWallet.setAuthHeader(authHeader);
        return customerService.credit(customerWallet);
    }

    @PostMapping("/debit")
    public CustomerWallet debit(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody CustomerWallet customerWallet) {
        customerWallet.setAuthHeader(authHeader);
        return customerService.debit(customerWallet);
    }

    @GetMapping("/balances")
    public CustomerWallet balances(@RequestHeader("Authorization") String authHeader, @RequestParam(name = "customerid") String id) {
        CustomerWallet customerWallet = new CustomerWallet();
        customerWallet.setId(Long.valueOf(id));
        customerWallet.setAuthHeader(authHeader);

        return customerService.checkBalance(customerWallet);

    }

    @GetMapping("/balancehistory")
    public List<BalanceHistory> balanceHistory(@RequestHeader("Authorization") String authHeader, @RequestParam(name = "customerid") String id) {
        return customerService.balanceHistory(authHeader, id);
    }

}
