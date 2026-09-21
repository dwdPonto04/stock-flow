package com.dwdponto04.stockflow.business.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record PatchUserRequestDTO(
        @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String name,

        @Email(message = "Informe um e-mail válido")
        @Size(max = 100, message = "O e-mail deve conter no máximo 100 caracteres")
        String email) {

}
