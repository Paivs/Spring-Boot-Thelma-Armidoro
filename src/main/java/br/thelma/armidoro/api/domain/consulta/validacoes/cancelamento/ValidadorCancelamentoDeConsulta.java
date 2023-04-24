package br.thelma.armidoro.api.domain.consulta.validacoes.cancelamento;

import br.thelma.armidoro.api.domain.consulta.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {

    void validar(DadosCancelamentoConsulta dados);

}
