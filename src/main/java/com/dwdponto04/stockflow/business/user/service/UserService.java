package com.dwdponto04.stockflow.business.user.service;

import com.dwdponto04.stockflow.business.user.dto.request.CreateUserRequestDTO;
import com.dwdponto04.stockflow.business.user.dto.request.UpdateUserRequestDTO;
import com.dwdponto04.stockflow.business.user.dto.response.UserResponseDTO;
import com.dwdponto04.stockflow.business.user.entity.User;
import com.dwdponto04.stockflow.business.user.enums.Role;
import com.dwdponto04.stockflow.business.user.mapper.UserMapper;
import com.dwdponto04.stockflow.infrastructure.exceptions.ConflictException;
import com.dwdponto04.stockflow.infrastructure.exceptions.ResourceNotFoundException;
import com.dwdponto04.stockflow.infrastructure.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(CreateUserRequestDTO createUserDTO) {
        String name = createUserDTO.name().trim();
        String email = validateAndNormalizeEmail(createUserDTO.email());

        validateEmailNotExists(email);

        CreateUserRequestDTO normalizedDTO =
                new CreateUserRequestDTO(
                        name,
                        email,
                        createUserDTO.password()
                );

        User user = UserMapper.toUser(normalizedDTO);
        user.setRole(Role.USER);

        userRepository.save(user);
    }
    public UserResponseDTO findById(Long id) {
        User user = findUserById(id);
        return UserMapper.toUserResponseDTO(user);
    }
    public UserResponseDTO findByEmail(String email) {
        String emailNormalized = validateAndNormalizeEmail(email);
        User user = userRepository.findByEmail(emailNormalized)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return UserMapper.toUserResponseDTO(user);
    }
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserResponseDTO)
                .toList();
    }
    public UserResponseDTO update(Long id,
                                  UpdateUserRequestDTO updateUserRequestDTO) {
        User user = findUserById(id);
        String name = updateUserRequestDTO.name().trim();
        String email = validateAndNormalizeEmail(updateUserRequestDTO.email());
        validateEmailNotExistsForAnotherUser(email, id);
        user.setName(name);
        user.setEmail(email);
        userRepository.save(user);
        return UserMapper.toUserResponseDTO(user);
    }
    public void delete(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }
    private void validateEmailNotExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("E-mail já cadastrado ");
        }
    }

    private String validateAndNormalizeEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }
        return email.trim().toLowerCase();
    }
    private User findUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        return user;
    }
    private void validateEmailNotExistsForAnotherUser(String email, Long id) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    if (!user.getId().equals(id)) {
                        throw new ConflictException("Esse e-mail já está cadastrado");
                    }
                });
    }
}

