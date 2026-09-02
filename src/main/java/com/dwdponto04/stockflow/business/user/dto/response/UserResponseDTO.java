package com.dwdponto04.stockflow.business.user.dto.response;

import com.dwdponto04.stockflow.business.user.enums.Role;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        Role role
) {
}
