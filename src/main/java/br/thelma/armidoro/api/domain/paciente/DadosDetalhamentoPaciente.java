package br.thelma.armidoro.api.domain.paciente;

import br.thelma.armidoro.api.domain.contatoEmergencia.ContatoEmergencia;
import br.thelma.armidoro.api.domain.endereco.Endereco;

import java.time.LocalDateTime;

public record DadosDetalhamentoPaciente(Long id,
                                        String nome,
                                        String email,
                                        String cpf,
                                        String telefone,
                                        String telefone_fixo,
                                        LocalDateTime nascimento,
                                        Endereco endereco,
                                        EstadoCivil estado_civil,
                                        String curso,
                                        String profissao,
                                        String cargo,
                                        String nacionalidade,
                                        ContatoEmergencia contatoEmergencia,
                                        boolean ativo) {

    public DadosDetalhamentoPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getTelefone(), paciente.getTelefone_fixo(), paciente.getNascimento(), paciente.getEndereco(), paciente.getEstado_civil(), paciente.getCurso(), paciente.getProfissao(), paciente.getCargo(), paciente.getNacionalidade(), paciente.getContatoEmergencia(), paciente.getAtivo());
    }
}
