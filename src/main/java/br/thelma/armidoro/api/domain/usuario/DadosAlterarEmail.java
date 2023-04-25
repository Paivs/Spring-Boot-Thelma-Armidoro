package br.thelma.armidoro.api.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosAlterarEmail(
        @Email
        @NotBlank
        String login
) {
}
