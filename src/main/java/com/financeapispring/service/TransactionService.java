package com.financeapispring.service;

import com.financeapispring.dto.TransactionDTO;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO);
    TransactionDTO save(TransactionDTO transactionDTO);
    List<TransactionDTO> findAllTransactions();
    Optional<TransactionDTO> findById(Long id);
    List<TransactionDTO> findByUserId(Long userId);
    void deleteById(Long id);
    List<TransactionDTO> findByCategoryId(Long categoryId);
    List<TransactionDTO> findByDateBetween(Date startDate, Date endDate);
    List<TransactionDTO> findByAmountGreaterThan(Double amount);
    List<TransactionDTO> findByAmountLessThan(Double amount);
}
