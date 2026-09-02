package com.dwdponto04.stockflow.business.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100,message = "O nome deve ter no máximo 100 caracteres")
        String name,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Informe um e-mail válido")
        @Size(max=100, message = "O e-mail deve conter no máximo 100 caracteres")
        String email,

        @NotBlank(message = "A senha é obrigatoria")
        @Size(min = 6,max = 10, message = "A senha deve conter no mínimo 6 e no máximo 10 caracteres")
        String password

) {}
