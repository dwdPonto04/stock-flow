package com.dwdponto04.stockflow.business.user.service;

import com.dwdponto04.stockflow.business.user.dto.request.CreateUserRequestDTO;
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

    private void validateEmailNotExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("E-mail já cadastrado ");
        }
    }

    public UserResponseDTO findById(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
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

    private UserResponseDTO toResponseDTO(User user) {

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole());
    }

    public List<UserResponseDTO> findAll(){
        return userRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

}
