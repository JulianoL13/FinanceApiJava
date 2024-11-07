package com.financeapispring.service;

import com.financeapispring.dto.UserDTO;
import java.util.Optional;

public interface UserService {
    UserDTO updateUser(Long id, UserDTO userDTO);
    UserDTO save(UserDTO userDTO);
    Optional<UserDTO> findById(Long id);
    Optional<UserDTO> findByEmail(String email);
    void deleteById(Long id);
}
