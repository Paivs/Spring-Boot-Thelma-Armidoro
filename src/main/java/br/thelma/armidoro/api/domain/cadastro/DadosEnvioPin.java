package br.thelma.armidoro.api.domain.cadastro;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEnvioPin(

        @NotBlank(message = "E-mail deve ser preenchido")
        @Email(message = "Formato de e-mail inválida")
        String login,

        @NotBlank(message = "PIN deve ser preenchido")
        @Pattern(regexp = "^[A-Z0-9]{5}$", message = "O pin deve possuir 5 caracteres")
        String pin
) {
}
