package com.financeapispring.controllers;

import com.financeapispring.dto.TransactionDTO;
import com.financeapispring.service.TransactionService;
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
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = transactionService.findAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        TransactionDTO createdTransaction = transactionService.save(transactionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(
        @PathVariable Long id, @RequestBody TransactionDTO transactionDTO) {
        TransactionDTO updatedTransaction = transactionService.updateTransaction(id, transactionDTO);
        return ResponseEntity.ok(updatedTransaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        Optional<TransactionDTO> transaction = transactionService.findById(id);
        return transaction.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByUserId(@PathVariable Long userId) {
        List<TransactionDTO> transactions = transactionService.findByUserId(userId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByCategoryId(@PathVariable Long categoryId) {
        List<TransactionDTO> transactions = transactionService.findByCategoryId(categoryId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/date")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByDateRange(
        @RequestParam Date startDate, @RequestParam Date endDate) {
        List<TransactionDTO> transactions = transactionService.findByDateBetween(startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/amount/greater")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAmountGreaterThan(@RequestParam Double amount) {
        List<TransactionDTO> transactions = transactionService.findByAmountGreaterThan(amount);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/amount/less")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAmountLessThan(@RequestParam Double amount) {
        List<TransactionDTO> transactions = transactionService.findByAmountLessThan(amount);
        return ResponseEntity.ok(transactions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransactionById(@PathVariable Long id) {
        transactionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
