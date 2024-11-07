package com.financeapispring.mapper;

import com.financeapispring.dto.UserDTO;
import com.financeapispring.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getNome());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }

    public User mapToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setNome(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setSenha(dto.getPassword());
        return user;
    }

    public void updateEntityFromDTO(UserDTO dto, User user) {
        user.setNome(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
    }
}
