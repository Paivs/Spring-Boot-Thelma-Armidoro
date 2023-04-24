package br.thelma.armidoro.api.domain.cadastro;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroAcessoCadastro(
        @NotBlank
        String login
) {
}
