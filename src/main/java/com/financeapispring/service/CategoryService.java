package com.financeapispring.service;

import com.financeapispring.dto.CategoryDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    List<CategoryDTO> getAllCategories();
    Optional<CategoryDTO> getCategoryByName(String name);
    Optional<CategoryDTO> getCategoryById(Long id);
    void deleteById(Long id);
    boolean categoryExistsByName(String name);
}
