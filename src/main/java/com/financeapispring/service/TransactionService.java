package com.financeapispring.service;

import com.financeapispring.dto.TransactionDTO;
import com.financeapispring.dto.TransactionProjection;
import com.financeapispring.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    Transaction updateTransaction(Long id, Transaction transaction);

    Transaction createTransaction(TransactionDTO payload);

    List<Transaction> findAllTransactions();
    Optional<Transaction> findById(Long id);
    List<Transaction> findByUserId(Long userId);
    void deleteById(Long id);
    List<Transaction> findByCategoryId(Long categoryId);
    List<Transaction> findByDateBetween(Date startDate, Date endDate);
    List<Transaction> findByAmountGreaterThan(Double amount);
    List<Transaction> findByAmountLessThan(Double amount);
    Page<TransactionProjection> findByAmountGreaterThanAndUserId(Double amount, Long userId, Pageable pageable);
    Page<TransactionProjection> findByAmountLessThanAndUserId(Double amount, Long userId, Pageable pageable);}
