package com.financeapispring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financeapispring.dto.CategoryDTO;
import com.financeapispring.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    private CategoryDTO categoryDTO;

    @BeforeEach
    public void setUp() {
        categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Test Category");
        categoryDTO.setDescription("This is a test category");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateCategory() throws Exception {
        Mockito.when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(categoryDTO);

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Test Category"))
            .andExpect(jsonPath("$.description").value("This is a test category"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUpdateCategory() throws Exception {
        Mockito.when(categoryService.updateCategory(anyLong(), any(CategoryDTO.class))).thenReturn(categoryDTO);

        mockMvc.perform(put("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Test Category"))
            .andExpect(jsonPath("$.description").value("This is a test category"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetCategoryById() throws Exception {
        Mockito.when(categoryService.getCategoryById(anyLong())).thenReturn(Optional.of(categoryDTO));

        mockMvc.perform(get("/api/categories/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Test Category"))
            .andExpect(jsonPath("$.description").value("This is a test category"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetCategoryByName() throws Exception {
        Mockito.when(categoryService.getCategoryByName(anyString())).thenReturn(Optional.of(categoryDTO));

        mockMvc.perform(get("/api/categories/byName/Test Category"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Test Category"))
            .andExpect(jsonPath("$.description").value("This is a test category"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAllCategories() throws Exception {
        List<CategoryDTO> categories = Collections.singletonList(categoryDTO);
        Mockito.when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/categories"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].name").value("Test Category"))
            .andExpect(jsonPath("$[0].description").value("This is a test category"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteCategoryById() throws Exception {
        Mockito.doNothing().when(categoryService).deleteById(anyLong());

        mockMvc.perform(delete("/api/categories/1"))
            .andExpect(status().isNoContent());
    }
}
