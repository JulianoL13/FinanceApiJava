package com.financeapispring.service;

import com.financeapispring.model.Transaction;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    Transaction save(Transaction transaction);
    List<Transaction> findAllTransaction();
    Optional<Transaction> findById(Long id);
    List<Transaction> findByUserId(Long userId);
    void deleteById(Long id);
    List<Transaction> findByCategoryId(Long categoryId);
    List<Transaction> findByDateBetween(Date startDate, Date endDate);
    List<Transaction> findByAmmountGreatherThan(Double amount);
    List<Transaction> findByAmmountLessThan(Double amount);
}
