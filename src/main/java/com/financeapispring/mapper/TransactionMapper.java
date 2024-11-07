package com.financeapispring.mapper;

import com.financeapispring.dto.TransactionDTO;
import com.financeapispring.model.Transaction;
import com.financeapispring.model.User;
import com.financeapispring.model.Category;
import com.financeapispring.repository.UserRepository;
import com.financeapispring.repository.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    private static final String ERROR_INVALID_USER_ID = "Invalid user ID";
    private static final String ERROR_INVALID_CATEGORY_ID = "Invalid category ID";

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TransactionMapper(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public TransactionDTO mapToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setDescription(transaction.getDescription());
        dto.setDate(transaction.getDate());
        dto.setType(transaction.getType());
        dto.setRecurring(transaction.getRecurring());
        dto.setCategoryId(transaction.getCategory().getId());
        dto.setUserId(transaction.getUser().getId());
        return dto;
    }

    public void updateEntityFromDTO(TransactionDTO dto, Transaction transaction) {
        transaction.setAmount(dto.getAmount());
        transaction.setDescription(dto.getDescription());
        transaction.setDate(dto.getDate());
        transaction.setType(dto.getType());
        transaction.setRecurring(dto.getRecurring());
        transaction.setUser(fetchUserById(dto.getUserId()));
        transaction.setCategory(fetchCategoryById(dto.getCategoryId()));
    }

    public Transaction mapToEntity(TransactionDTO dto) {
        Transaction transaction = new Transaction();
        if (dto.getId() != null) {
            transaction.setId(dto.getId());
        }
        setTransactionAttributes(transaction, dto);
        transaction.setUser(fetchUserById(dto.getUserId()));
        transaction.setCategory(fetchCategoryById(dto.getCategoryId()));
        return transaction;
    }

    private void setTransactionAttributes(Transaction transaction, TransactionDTO dto) {
        transaction.setAmount(dto.getAmount());
        transaction.setDescription(dto.getDescription());
        transaction.setDate(dto.getDate());
        transaction.setType(dto.getType());
        transaction.setRecurring(dto.getRecurring());
    }

    private User fetchUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException(ERROR_INVALID_USER_ID));
    }

    private Category fetchCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
            .orElseThrow(() -> new IllegalArgumentException(ERROR_INVALID_CATEGORY_ID));
    }
}
