package com.financeapispring.dto;

import com.financeapispring.model.enums.UserRole;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private UserRole role;
}
