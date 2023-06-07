package br.thelma.armidoro.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import br.thelma.armidoro.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        String telefoneFixo,
        DadosEndereco endereco) {
}
