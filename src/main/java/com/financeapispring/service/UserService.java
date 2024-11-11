package com.financeapispring.service;

import com.financeapispring.model.User;
import com.financeapispring.model.enums.UserRole;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {
    User updateUser(Long id, User user);
    User save(User user);
    User updateUserRole(Long id, UserRole role);
    Optional<User> findById(Long id);
    UserDetails findByEmail(String email);
    void deleteById(Long id);
}
