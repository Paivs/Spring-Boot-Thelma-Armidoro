package br.thelma.armidoro.api.domain.usuario;

import br.thelma.armidoro.api.domain.cadastro.DadosEnvioPin;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosAlterarSenha(
        @NotBlank
        String login,
        @NotBlank
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "A senha não preenche os requisitos minímos")
        String senha,

        @Valid
        DadosEnvioPin pin
) {
}
