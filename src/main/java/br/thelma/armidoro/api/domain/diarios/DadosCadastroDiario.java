package br.thelma.armidoro.api.domain.diarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroDiario(
        @NotNull
        boolean tipo, //0 - Sonhos / 1 - Diario

        @NotBlank
        String titulo,

        @NotBlank
        String texto,

        @NotNull
        Long paciente
) {
}
