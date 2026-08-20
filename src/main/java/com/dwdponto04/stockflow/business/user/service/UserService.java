package com.dwdponto04.stockflow.business.user.service;

import com.dwdponto04.stockflow.business.user.dto.CreateUserDTO;
import com.dwdponto04.stockflow.business.user.entity.User;
import com.dwdponto04.stockflow.business.user.enums.Role;
import com.dwdponto04.stockflow.infrastructure.exceptions.ConflictException;
import com.dwdponto04.stockflow.infrastructure.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(CreateUserDTO createUserDTO) {
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
        private void validateEmailNotExists(String email){
            if (userRepository.existsByEmail(email)) {
                throw new ConflictException("E-mail já cadastrado " );
            }
        }

    }
