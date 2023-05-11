package br.thelma.armidoro.api.domain.cadastro;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroAcessoSenha(
        @NotBlank
        String login
) {
}
