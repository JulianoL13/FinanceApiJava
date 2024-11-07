package com.financeapispring.service.impl;

import com.financeapispring.dto.UserDTO;
import com.financeapispring.exception.ResourceNotFoundException;
import com.financeapispring.model.User;
import com.financeapispring.repository.UserRepository;
import com.financeapispring.service.UserService;
import com.financeapispring.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            userMapper.updateEntityFromDTO(userDTO, existingUser);
            return userMapper.mapToDTO(userRepository.save(existingUser));
        } else {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.mapToEntity(userDTO);
        return userMapper.mapToDTO(userRepository.save(user));
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id)
            .map(userMapper::mapToDTO);
    }

    @Override
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email)
            .map(userMapper::mapToDTO);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
