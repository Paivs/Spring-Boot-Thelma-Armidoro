package br.thelma.armidoro.api.domain.consulta.validacoes.agendamento;

import br.thelma.armidoro.api.domain.consulta.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsulta {

    void validar(DadosAgendamentoConsulta dados);

}
