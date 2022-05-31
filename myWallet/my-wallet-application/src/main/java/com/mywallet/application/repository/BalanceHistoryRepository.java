package com.mywallet.application.repository;

import com.mywallet.application.model.BalanceHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalanceHistoryRepository extends MongoRepository<BalanceHistory, Long> {
    List<BalanceHistory> findByCustomerId(long id);
}
