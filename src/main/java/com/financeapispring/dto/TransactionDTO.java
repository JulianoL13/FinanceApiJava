package com.financeapispring.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class TransactionDTO {
    private Long id;
    private Double amount;
    private String description;
    private Date date;
    private String type;
    private Boolean recurring;
    private Long categoryId;
    private Long userId;
}
