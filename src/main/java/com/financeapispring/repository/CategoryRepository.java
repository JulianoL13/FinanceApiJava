package com.financeapispring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.financeapispring.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    List<Category> findByActiveTrue();

    boolean existsByName(String name);
}
