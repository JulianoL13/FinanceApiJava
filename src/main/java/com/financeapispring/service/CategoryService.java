package com.financeapispring.service;

import com.financeapispring.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category createCategory(Category category);
    Category updateCategory(Long id, Category category);
    List<Category> findAllCategories();
    Optional<Category> findByName(String name);
    Optional<Category> findCategoryById(Long id);
    void deleteById(Long id);
    boolean categoryExistsByName(String name);
}
