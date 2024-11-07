package com.financeapispring.service.impl;

import com.financeapispring.model.Category;
import com.financeapispring.repository.CategoryRepository;
import com.financeapispring.service.CategoryService;
import com.financeapispring.mapper.CategoryMapper;
import com.financeapispring.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.mapToEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.mapToDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isPresent()) {
            Category updatedCategory = existingCategory.get();
            categoryMapper.updateEntityFromDTO(categoryDTO, updatedCategory);
            Category savedCategory = categoryRepository.save(updatedCategory);
            return categoryMapper.mapToDTO(savedCategory);
        } else {
            throw new IllegalArgumentException("Category with id " + id + " not found");
        }
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
            .map(categoryMapper::mapToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryDTO> getCategoryByName(String name) {
        Optional<Category> category = categoryRepository.findByName(name);
        return category.map(categoryMapper::mapToDTO);
    }

    @Override
    public Optional<CategoryDTO> getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(categoryMapper::mapToDTO);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public boolean categoryExistsByName(String name) {
        return categoryRepository.existsByName(name);
    }
}
