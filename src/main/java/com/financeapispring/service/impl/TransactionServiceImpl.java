package com.financeapispring.service.impl;

import com.financeapispring.dto.TransactionDTO;
import com.financeapispring.dto.TransactionProjection;
import com.financeapispring.model.Category;
import com.financeapispring.model.Transaction;
import com.financeapispring.model.User;
import com.financeapispring.repository.TransactionRepository;
import com.financeapispring.service.CategoryService;
import com.financeapispring.service.TransactionService;
import com.financeapispring.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, UserService userService, CategoryService categoryService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public Transaction updateTransaction(Long id, Transaction transaction) {
        transaction.setId(id);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction createTransaction(TransactionDTO payload) {
        try {
            User user = userService.findById(payload.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
            Category category = categoryService.findCategoryById(payload.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
            Transaction transaction = buildTransactionFromPayload(payload, user, category);
            return transactionRepository.save(transaction);
        } catch (RuntimeException e) {
            System.err.println("RuntimeException: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An unexpected error occurred");
        }
    }

    private Transaction buildTransactionFromPayload(TransactionDTO payload, User user, Category category) {
        Transaction transaction = new Transaction();
        transaction.setAmount(payload.getAmount());
        transaction.setDescription(payload.getDescription());
        transaction.setDate(payload.getTransactionDate());
        transaction.setType(payload.getType());
        transaction.setRecurring(payload.getRecurring());
        transaction.setUser(user);
        transaction.setCategory(category);
        return transaction;
    }

    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
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
        return transactionRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public List<Transaction> findByAmountGreaterThan(Double amount) {
        return transactionRepository.findByAmountGreaterThan(amount);
    }

    @Override
    public List<Transaction> findByAmountLessThan(Double amount) {
        return transactionRepository.findByAmountLessThan(amount);
    }
    @Override
    public Page<TransactionProjection> findByAmountGreaterThanAndUserId(Double amount, Long userId, Pageable pageable) {
        return transactionRepository.findByAmountGreaterThanAndUserId(amount, userId, pageable);
    }

    @Override
    public Page<TransactionProjection> findByAmountLessThanAndUserId(Double amount, Long userId, Pageable pageable) {
        return transactionRepository.findByAmountLessThanAndUserId(amount, userId, pageable);
    }
}
