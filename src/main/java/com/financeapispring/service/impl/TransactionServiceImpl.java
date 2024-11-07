package com.financeapispring.service.impl;

import com.financeapispring.dto.TransactionDTO;
import com.financeapispring.model.Transaction;
import com.financeapispring.repository.TransactionRepository;
import com.financeapispring.service.TransactionService;
import com.financeapispring.mapper.TransactionMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public List<TransactionDTO> findAllTransactions() {
        return transactionRepository.findAll().stream()
            .map(transactionMapper::mapToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO) {
        Optional<Transaction> existingTransaction = transactionRepository.findById(id);
        if (existingTransaction.isPresent()) {
            Transaction updatedTransaction = existingTransaction.get();
            transactionMapper.updateEntityFromDTO(transactionDTO, updatedTransaction);
            return transactionMapper.mapToDTO(transactionRepository.save(updatedTransaction));
        } else {
            throw new IllegalArgumentException("Transaction with ID " + id + " not found");
        }
    }

    @Override
    public TransactionDTO save(TransactionDTO transactionDTO) {
        Transaction transaction = transactionMapper.mapToEntity(transactionDTO);
        return transactionMapper.mapToDTO(transactionRepository.save(transaction));
    }

    @Override
    public Optional<TransactionDTO> findById(Long id) {
        return transactionRepository.findById(id)
            .map(transactionMapper::mapToDTO);
    }

    @Override
    public List<TransactionDTO> findByUserId(Long userId) {
        return transactionRepository.findByUserId(userId).stream()
            .map(transactionMapper::mapToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public List<TransactionDTO> findByCategoryId(Long categoryId) {
        return transactionRepository.findByCategoryId(categoryId).stream()
            .map(transactionMapper::mapToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findByDateBetween(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null");
        }
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        return transactionRepository.findByDateBetween(startDate, endDate).stream()
            .map(transactionMapper::mapToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findByAmountGreaterThan(Double amount) {
        return transactionRepository.findByAmountGreaterThan(amount).stream()
            .map(transactionMapper::mapToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findByAmountLessThan(Double amount) {
        return transactionRepository.findByAmountLessThan(amount).stream()
            .map(transactionMapper::mapToDTO)
            .collect(Collectors.toList());
    }
}
