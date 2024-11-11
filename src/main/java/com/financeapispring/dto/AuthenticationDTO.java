package com.financeapispring.dto;

import com.financeapispring.model.enums.UserRole;

public record AuthenticationDTO(String name, String email, String password, UserRole role) {

}
