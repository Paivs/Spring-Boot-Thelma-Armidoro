package br.thelma.armidoro.api.domain.paciente;

import br.thelma.armidoro.api.domain.contatoEmergencia.DadosContatoEmergencia;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import br.thelma.armidoro.api.domain.endereco.DadosEndereco;

import java.time.LocalDateTime;

public record DadosCadastroPaciente(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(regexp = "^\\+\\d{1,3}\s\\(\\d{2}\\)\s\\d{9}", message = "Formato do telefone inválido -> +55 11 987654321")
        String telefone,
        @Pattern(regexp = "^\\+\\d{1,3}\s\\(\\d{2}\\)\s\\d{8}", message = "Formato do telefone inválido -> +55 11 987654321")
        String telefone_fixo,
        @NotBlank
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}", message = "Formato do cpf inválido -> 123.123.123-12")
        String cpf,

        @NotNull
        @Past
        LocalDateTime nascimento,

        @NotNull
        EstadoCivil estado_civil,

        @NotBlank
        String curso,

        @NotBlank
        String profissao,

        @NotBlank
        String cargo,

        @NotBlank
        String nacionalidade,

        @NotNull @Valid DadosEndereco endereco,

        @NotNull @Valid DadosContatoEmergencia contatoEmergencia) {
}
