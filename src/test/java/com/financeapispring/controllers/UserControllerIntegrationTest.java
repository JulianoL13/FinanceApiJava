package com.financeapispring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financeapispring.dto.UserDTO;
import com.financeapispring.model.enums.UserRole;
import com.financeapispring.service.UserService;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("Test User");
        userDTO.setEmail("test@domain.com");
        userDTO.setPassword("password");
        userDTO.setRole(UserRole.USER);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateUser() throws Exception {
        Mockito.when(userService.save(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Test User"))
            .andExpect(jsonPath("$.email").value("test@domain.com"))
            .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUpdateUser() throws Exception {
        Mockito.when(userService.updateUser(anyLong(), any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Test User"))
            .andExpect(jsonPath("$.email").value("test@domain.com"))
            .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetUserById() throws Exception {
        Mockito.when(userService.findById(anyLong())).thenReturn(Optional.of(userDTO));

        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Test User"))
            .andExpect(jsonPath("$.email").value("test@domain.com"))
            .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetUserByEmail() throws Exception {
        Mockito.when(userService.findByEmail(anyString())).thenReturn(Optional.of(userDTO));

        mockMvc.perform(get("/api/users/email")
                .param("email", "test@domain.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Test User"))
            .andExpect(jsonPath("$.email").value("test@domain.com"))
            .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteUserById() throws Exception {
        Mockito.doNothing().when(userService).deleteById(anyLong());

        mockMvc.perform(delete("/api/users/1"))
            .andExpect(status().isNoContent());
    }
}
