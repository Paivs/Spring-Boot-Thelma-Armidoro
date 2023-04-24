package br.thelma.armidoro.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import br.thelma.armidoro.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
