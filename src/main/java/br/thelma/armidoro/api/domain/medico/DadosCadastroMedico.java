package br.thelma.armidoro.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import br.thelma.armidoro.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(regexp = "^\\+\\d{1,3}\s\\(\\d{2}\\)\s\\d{9}", message = "Formato do telefone inválido -> +55 11 987654321")
        String telefone,
        @NotBlank
        @Pattern(regexp = "\\d{2}\\/\\d{3}\\.\\d{3}")
        String crp,

        @NotNull @Valid DadosEndereco endereco) {
}
