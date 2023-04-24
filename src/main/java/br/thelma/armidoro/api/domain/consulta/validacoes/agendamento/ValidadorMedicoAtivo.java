package br.thelma.armidoro.api.domain.consulta.validacoes.agendamento;

import br.thelma.armidoro.api.domain.ValidacaoException;
import br.thelma.armidoro.api.domain.consulta.DadosAgendamentoConsulta;
import br.thelma.armidoro.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private MedicoRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        //escolha do medico opcional
        if (dados.idMedico() == null) {
            return;
        }

        var medicoEstaAtivo = repository.findAtivoById(dados.idMedico());
        if (!medicoEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com médico excluído");
        }
    }

}
