package com.financeapispring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financeapispring.dto.TransactionDTO;
import com.financeapispring.service.TransactionService;
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

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    private TransactionDTO transactionDTO;

    @BeforeEach
    public void setUp() {
        transactionDTO = new TransactionDTO();
        transactionDTO.setId(1L);
        transactionDTO.setUserId(1L);
        transactionDTO.setCategoryId(1L);
        transactionDTO.setAmount(100.0);
        transactionDTO.setDate(new Date(System.currentTimeMillis()));
        transactionDTO.setDescription("Test Transaction");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateTransaction() throws Exception {
        Mockito.when(transactionService.save(any(TransactionDTO.class))).thenReturn(transactionDTO);

        mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.amount").value(100.0))
            .andExpect(jsonPath("$.description").value("Test Transaction"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUpdateTransaction() throws Exception {
        Mockito.when(transactionService.updateTransaction(anyLong(), any(TransactionDTO.class))).thenReturn(transactionDTO);

        mockMvc.perform(put("/api/transactions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.amount").value(100.0))
            .andExpect(jsonPath("$.description").value("Test Transaction"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetTransactionById() throws Exception {
        Mockito.when(transactionService.findById(anyLong())).thenReturn(Optional.of(transactionDTO));

        mockMvc.perform(get("/api/transactions/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.amount").value(100.0))
            .andExpect(jsonPath("$.description").value("Test Transaction"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAllTransactions() throws Exception {
        List<TransactionDTO> transactions = Collections.singletonList(transactionDTO);
        Mockito.when(transactionService.findAllTransactions()).thenReturn(transactions);

        mockMvc.perform(get("/api/transactions"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].amount").value(100.0))
            .andExpect(jsonPath("$[0].description").value("Test Transaction"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetTransactionsByUserId() throws Exception {
        List<TransactionDTO> transactions = Collections.singletonList(transactionDTO);
        Mockito.when(transactionService.findByUserId(anyLong())).thenReturn(transactions);

        mockMvc.perform(get("/api/transactions/user/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].amount").value(100.0))
            .andExpect(jsonPath("$[0].description").value("Test Transaction"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetTransactionsByCategoryId() throws Exception {
        List<TransactionDTO> transactions = Collections.singletonList(transactionDTO);
        Mockito.when(transactionService.findByCategoryId(anyLong())).thenReturn(transactions);

        mockMvc.perform(get("/api/transactions/category/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].amount").value(100.0))
            .andExpect(jsonPath("$[0].description").value("Test Transaction"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetTransactionsByDateRange() throws Exception {
        List<TransactionDTO> transactions = Collections.singletonList(transactionDTO);
        Mockito.when(transactionService.findByDateBetween(any(Date.class), any(Date.class))).thenReturn(transactions);

        mockMvc.perform(get("/api/transactions/date")
                .param("startDate", "2023-01-01")
                .param("endDate", "2023-12-31"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].amount").value(100.0))
            .andExpect(jsonPath("$[0].description").value("Test Transaction"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetTransactionsByAmountGreaterThan() throws Exception {
        List<TransactionDTO> transactions = Collections.singletonList(transactionDTO);
        Mockito.when(transactionService.findByAmountGreaterThan(anyDouble())).thenReturn(transactions);

        mockMvc.perform(get("/api/transactions/amount/greater")
                .param("amount", "50.0"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].amount").value(100.0))
            .andExpect(jsonPath("$[0].description").value("Test Transaction"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetTransactionsByAmountLessThan() throws Exception {
        List<TransactionDTO> transactions = Collections.singletonList(transactionDTO);
        Mockito.when(transactionService.findByAmountLessThan(anyDouble())).thenReturn(transactions);

        mockMvc.perform(get("/api/transactions/amount/less")
                .param("amount", "150.0"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].amount").value(100.0))
            .andExpect(jsonPath("$[0].description").value("Test Transaction"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteTransactionById() throws Exception {
        Mockito.doNothing().when(transactionService).deleteById(anyLong());

        mockMvc.perform(delete("/api/transactions/1"))
            .andExpect(status().isNoContent());
    }
}
