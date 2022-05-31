package com.mywallet.application.repository;

import com.mywallet.application.model.CustomerWallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerWalletRepository extends MongoRepository<CustomerWallet, Long> {
}
