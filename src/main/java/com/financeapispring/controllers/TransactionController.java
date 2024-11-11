package com.financeapispring.controllers;

import com.financeapispring.dto.TransactionDTO;
import com.financeapispring.dto.TransactionProjection;
import com.financeapispring.model.Transaction;
import com.financeapispring.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO payload) {
        try {
            Transaction createdTransaction = transactionService.createTransaction(payload);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(
        @PathVariable Long id, @RequestBody Transaction transaction) {
        Transaction updatedTransaction = transactionService.updateTransaction(id, transaction);
        return ResponseEntity.ok(updatedTransaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.findById(id);
        return transaction.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserId(@PathVariable Long userId) {
        List<Transaction> transactions = transactionService.findByUserId(userId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Transaction>> getTransactionsByCategoryId(@PathVariable Long categoryId) {
        List<Transaction> transactions = transactionService.findByCategoryId(categoryId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/date")
    public ResponseEntity<List<Transaction>> getTransactionsByDateRange(
        @RequestParam Date startDate, @RequestParam Date endDate) {
        List<Transaction> transactions = transactionService.findByDateBetween(startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/amount/greater")
    public ResponseEntity<Page<TransactionProjection>> getTransactionsByAmountGreaterThanAndUserId(
        @RequestParam Double amount,
        @RequestParam Long userId,
        @RequestParam int page,
        @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TransactionProjection> transactions = transactionService.findByAmountGreaterThanAndUserId(amount, userId, pageable);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/amount/less")
    public ResponseEntity<Page<TransactionProjection>> getTransactionsByAmountLessThanAndUserId(
        @RequestParam Double amount,
        @RequestParam Long userId,
        @RequestParam int page,
        @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TransactionProjection> transactions = transactionService.findByAmountLessThanAndUserId(amount, userId, pageable);
        return ResponseEntity.ok(transactions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransactionById(@PathVariable Long id) {
        transactionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
