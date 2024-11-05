package com.financeapispring.service.impl;

import com.financeapispring.model.Transaction;
import com.financeapispring.repository.TransactionRepository;
import com.financeapispring.service.TransactionService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Transaction> findByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    @Override
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public List<Transaction> findByCategoryId(Long categoryId) {
        return transactionRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Transaction> findByDateBetween(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null");
        }
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        return transactionRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public List<Transaction> findByAmmountGreatherThan(Double amount) {
        return transactionRepository.findByAmountGreaterThan(amount);
    }
    @Override
    public List<Transaction> findByAmmountLessThan(Double amount) {
        return transactionRepository.findByAmountLessThan(amount);
    }
}

