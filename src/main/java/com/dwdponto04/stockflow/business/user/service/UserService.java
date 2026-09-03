package com.dwdponto04.stockflow.business.user.service;

import com.dwdponto04.stockflow.business.user.dto.request.CreateUserRequestDTO;
import com.dwdponto04.stockflow.business.user.dto.request.UpdateUserRequestDTO;
import com.dwdponto04.stockflow.business.user.dto.response.UserResponseDTO;
import com.dwdponto04.stockflow.business.user.entity.User;
import com.dwdponto04.stockflow.business.user.enums.Role;
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
        String email = createUserDTO.email().trim().toLowerCase();

        validateEmailNotExists(email);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        //todo implementar o algoritmo de hash para password no futuro.
        user.setPassword(createUserDTO.password());
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    public UserResponseDTO findById(Long id) {
        User user = findUserById(id);
        return toResponseDTO(user);
    }

    public UserResponseDTO findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("E-mail inválido");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return toResponseDTO(user);
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public UserResponseDTO update(Long id,
                                  UpdateUserRequestDTO updateUserRequestDTO) {
        User user = findUserById(id);
        String name = updateUserRequestDTO.name().trim();
        String email = updateUserRequestDTO.email().trim().toLowerCase();
        validateEmailNotExistsForAnotherUser(email, id);
        user.setName(name);
        user.setEmail(email);
        userRepository.save(user);
        return toResponseDTO(user);
    }

    private void validateEmailNotExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("E-mail já cadastrado ");
        }
    }

    private User findUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        return user;
    }

    private UserResponseDTO toResponseDTO(User user) {

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole());
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

