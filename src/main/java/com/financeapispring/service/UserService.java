package com.financeapispring.service;

import com.financeapispring.model.User;
import java.util.Optional;

public interface UserService {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    void deleteById(Long id);
}
