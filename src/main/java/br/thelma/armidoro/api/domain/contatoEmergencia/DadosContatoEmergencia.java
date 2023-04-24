package br.thelma.armidoro.api.domain.contatoEmergencia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosContatoEmergencia(
        @NotBlank
        String contato_nome,

        @NotBlank
        String contato_vinculo,

        @NotBlank
        @Pattern(regexp = "^\\+\\d{1,3}\s\\(\\d{2}\\)\s\\d{9}", message = "Formato do telefone inválido -> +55 11 987654321")
        String contato_telefone
) {

}
