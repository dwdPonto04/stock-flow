package com.dwdponto04.stockflow.business.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequestDTO(
        @NotBlank(message = "A senha atual é obrigatória")
        String currentPassword,

        @NotBlank(message = "A Nova senha é obrigatória")
        @Size(min = 6,max = 10, message = "A senha deve conter no mínimo 6 e no máximo 10 caracteres")
        String newPassword,

        @NotBlank(message = "A confirmação da senha é obrigatória")
        @Size(min = 6,max = 10, message = "A senha deve conter no mínimo 6 e no máximo 10 caracteres")
        String confirmNewPassword

) {
}
