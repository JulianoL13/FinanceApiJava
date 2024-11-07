package com.financeapispring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.financeapispring.model.Category;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
    @Query("SELECT e FROM Category e WHERE LOWER(e.name) = LOWER(:name)")
    Optional<Category> findByName(String name);
}
