package br.thelma.armidoro.api.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Normalized;

public record DadosCadastroUsuario(
        @NotBlank
        @Email
        String login,

        @NotBlank
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "A senha não preenche os requisitos minímos")
        String senha
) {
}
