package br.thelma.armidoro.api.domain.medico;

import br.thelma.armidoro.api.domain.endereco.Endereco;

public record DadosDetalhamentoMedico(Long id, String nome, String email, String crp, String telefone, Endereco endereco) {

    public DadosDetalhamentoMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrp(), medico.getTelefone(), medico.getEndereco());
    }
}
