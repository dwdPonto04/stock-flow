package com.dwdponto04.stockflow.business.user.mapper;

import com.dwdponto04.stockflow.business.user.dto.request.CreateUserRequestDTO;
import com.dwdponto04.stockflow.business.user.dto.response.UserResponseDTO;
import com.dwdponto04.stockflow.business.user.entity.User;

public class UserMapper {


    public static User toUser(CreateUserRequestDTO createUserRequestDTO){
        User user = new User();

        user.setName(createUserRequestDTO.name());
        user.setEmail(createUserRequestDTO.email());
        user.setPassword(createUserRequestDTO.password());

        return user;
    }

    public static UserResponseDTO toUserResponseDTO(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
