package com.financeapispring.mapper;

import com.financeapispring.dto.CategoryDTO;
import com.financeapispring.model.Category;
import com.financeapispring.model.User;
import com.financeapispring.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    private static final String ERROR_INVALID_USER_ID = "Invalid user ID";

    private final UserRepository userRepository;

    public CategoryMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CategoryDTO mapToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setUserId(category.getUser().getId());
        return dto;
    }

    public Category mapToEntity(CategoryDTO dto) {
        Category category = new Category();
        if (dto.getId() != null) {
            category.setId(dto.getId());
        }
        setCategoryAttributes(category, dto);
        category.setUser(fetchUserById(dto.getUserId()));
        return category;
    }

    public void updateEntityFromDTO(CategoryDTO dto, Category category) {
        setCategoryAttributes(category, dto);
        if (dto.getUserId() != null && !dto.getUserId().equals(category.getUser().getId())) {
            category.setUser(fetchUserById(dto.getUserId()));
        }
    }

    private void setCategoryAttributes(Category category, CategoryDTO dto) {
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
    }

    private User fetchUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException(ERROR_INVALID_USER_ID));
    }
}
