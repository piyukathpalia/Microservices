package com.mywallet.application.service;

import com.mywallet.application.repository.BalanceHistoryRepository;
import com.mywallet.application.repository.CustomerRepository;
import com.mywallet.application.repository.CustomerWalletRepository;
import com.mywallet.application.dto.AccessTokenResponse;
import com.mywallet.application.dto.CustomerCredentials;
import com.mywallet.application.model.BalanceHistory;
import com.mywallet.application.model.Customer;
import com.mywallet.application.model.CustomerWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

import org.apache.commons.codec.binary.Base64;

@Service
public class CustomerService {

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    PasswordGeneratorService passwordGeneratorService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerWalletRepository customerWalletRepository;

    @Autowired
    private BalanceHistoryRepository balanceHistoryRepository;

    public Customer save(Customer customer) {
        customer.setId(sequenceGeneratorService.generateSequence(Customer.SEQUENCE_NAME));
        customer.setPassword(passwordGeneratorService.generateRandomPassword());
        return customerRepository.save(customer);
    }

    public AccessTokenResponse generateAccessToken(CustomerCredentials customerCredentials) {
        long id = customerCredentials.getId();
        String password = customerCredentials.getPassword();
        String auth = id + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String accessToken = "Bearer " + encodedAuth;
        AccessTokenResponse accessTokenResponse = new AccessTokenResponse(accessToken);
        return accessTokenResponse;
    }

    public CustomerWallet credit(CustomerWallet customerWallet) {

      Customer customer = customerRepository.findById(customerWallet.getId()).get();

      //CustomerCredentials customerCredentials = decryptAuthToken(customerWallet.getAuthHeader());

      //if (customerCredentials.getId() == customer.getId()
        //      && customerCredentials.getPassword().equalsIgnoreCase(customer.getPassword())) {
            double creditAmount = customerWallet.getBalance();

            Optional<CustomerWallet> savedCustomerWalletOptional = customerWalletRepository.findById(customerWallet.getId());
            if(savedCustomerWalletOptional.isPresent()) {
                CustomerWallet savedCustomerWallet = savedCustomerWalletOptional.get();
                if(savedCustomerWallet.getBalance() > 0) {
                    customerWallet.setBalance(creditAmount +savedCustomerWallet.getBalance());
                }
            }

            BalanceHistory balanceHistory = createBalanceHistory(customerWallet.getId(), creditAmount, customerWallet.getBalance(), "Credit", "Bank Transfer");
            balanceHistoryRepository.save(balanceHistory);

            return customerWalletRepository.save(customerWallet);
      //} else {
        //  throw new RuntimeException("Invalid token");
      //}

    }

    private BalanceHistory createBalanceHistory(long id, double amount, double balance, String transactionType, String category) {
        BalanceHistory balanceHistory = new BalanceHistory();
        balanceHistory.setId(sequenceGeneratorService.generateSequence(BalanceHistory.SEQUENCE_NAME));
        balanceHistory.setCustomerId(id);
        balanceHistory.setAmount(amount);
        balanceHistory.setBalanceAfterTransaction(balance);
        balanceHistory.setTransactionType(transactionType);
        balanceHistory.setCategory(category);
        balanceHistory.setTransactionDate(new Date(System.currentTimeMillis()));
        return balanceHistory;
    }

    private CustomerCredentials decryptAuthToken(String authHeader) {
        String encodeIdPassword = authHeader.substring(7);
        byte[] decodedAuth = Base64.decodeBase64(encodeIdPassword.getBytes(StandardCharsets.UTF_8));
        String[] decodedIdPassword = String.valueOf(decodedAuth).split(":");
        Arrays.stream(decodedIdPassword).forEach(System.out::println);
        CustomerCredentials customerCredentials = new CustomerCredentials(Long.valueOf(decodedIdPassword[0]), decodedIdPassword[1]);
        return customerCredentials;
    }

    public CustomerWallet debit(CustomerWallet customerWallet) {

        Customer customer = customerRepository.findById(customerWallet.getId()).get();

        //CustomerCredentials customerCredentials = decryptAuthToken(customerWallet.getAuthHeader());

        //if (customerCredentials.getId() == customer.getId()
        //      && customerCredentials.getPassword().equalsIgnoreCase(customer.getPassword())) {
            double debitAmount = customerWallet.getBalance();
            Optional<CustomerWallet> savedCustomerWalletOptional = customerWalletRepository.findById(customerWallet.getId());
            if(savedCustomerWalletOptional.isPresent()) {
                CustomerWallet savedCustomerWallet = savedCustomerWalletOptional.get();
                if(savedCustomerWallet.getBalance() > 0 && savedCustomerWallet.getBalance() - debitAmount > 0) {
                    customerWallet.setBalance(savedCustomerWallet.getBalance() - debitAmount);

                    BalanceHistory balanceHistory = createBalanceHistory(customerWallet.getId(), debitAmount, customerWallet.getBalance(), "Debit", "Retail Shopping");
                    balanceHistoryRepository.save(balanceHistory);

                } else {
                    throw new RuntimeException("Insufficient balance in the customer account");
                }

            }

        return customerWalletRepository.save(customerWallet);
        //} else {
        //  throw new RuntimeException("Invalid token");
        //}

    }

    public CustomerWallet checkBalance(CustomerWallet customerWallet) {

        Customer customer = customerRepository.findById(customerWallet.getId()).get();

        //CustomerCredentials customerCredentials = decryptAuthToken(customerWallet.getAuthHeader());

        //if (customerCredentials.getId() == customer.getId()
        //      && customerCredentials.getPassword().equalsIgnoreCase(customer.getPassword())) {
            Optional<CustomerWallet> savedCustomerWalletOptional = customerWalletRepository.findById(customerWallet.getId());
            if(savedCustomerWalletOptional.isPresent()) {
                CustomerWallet savedCustomerWallet = savedCustomerWalletOptional.get();
                if(savedCustomerWallet.getBalance() > 0) {
                    customerWallet.setBalance(savedCustomerWallet.getBalance());
                }
            }
            return customerWallet;
        //} else {
        //  throw new RuntimeException("Invalid token");
        //}

    }

    public List<BalanceHistory> balanceHistory(String authHeader, String id) {

        Customer customer = customerRepository.findById(Long.valueOf(id)).get();

        //CustomerCredentials customerCredentials = decryptAuthToken(authHeader));

        //if (customerCredentials.getId() == customer.getId()
        //      && customerCredentials.getPassword().equalsIgnoreCase(customer.getPassword())) {

        List<BalanceHistory> balanceHistories = balanceHistoryRepository.findByCustomerId(customer.getId());
        if(!balanceHistories.isEmpty()) {
            Collections.sort(balanceHistories, Comparator.comparing(BalanceHistory::getTransactionDate));
        }
        return balanceHistories;
        //} else {
        //  throw new RuntimeException("Invalid token");
        //}

    }
}
