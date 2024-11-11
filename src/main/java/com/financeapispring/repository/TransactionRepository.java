package com.financeapispring.repository;

import com.financeapispring.dto.TransactionProjection;
import com.financeapispring.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId")
    List<Transaction> findByUserId(@Param("userId") Long userId);

    @Query("SELECT t FROM Transaction t WHERE t.category.id = :categoryId")
    List<Transaction> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT t FROM Transaction t WHERE t.date BETWEEN :startDate AND :endDate")
    List<Transaction> findByDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT t FROM Transaction t WHERE t.amount > :amount")
    List<Transaction> findByAmountGreaterThan(@Param("amount") Double amount);

    @Query("SELECT t FROM Transaction t WHERE t.amount < :amount")
    List<Transaction> findByAmountLessThan(@Param("amount") Double amount);

    @Query("SELECT t.id AS id, t.amount AS amount FROM Transaction t WHERE t.amount > :amount AND t.user.id = :userId")
    Page<TransactionProjection> findByAmountGreaterThanAndUserId(@Param("amount") Double amount, @Param("userId") Long userId, Pageable pageable);

    @Query("SELECT t.id AS id, t.amount AS amount FROM Transaction t WHERE t.amount < :amount AND t.user.id = :userId")
    Page<TransactionProjection> findByAmountLessThanAndUserId(@Param("amount") Double amount, @Param("userId") Long userId, Pageable pageable);
}
