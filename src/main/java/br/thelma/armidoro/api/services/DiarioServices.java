package br.thelma.armidoro.api.services;

import br.thelma.armidoro.api.domain.ValidacaoException;
import br.thelma.armidoro.api.domain.diarios.*;
import br.thelma.armidoro.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;


@Service
public class DiarioServices {

    @Autowired
    private DiarioSonhosRepository diarioSonhosRepository;

    @Autowired
    private DiarioEmocoesRepository diarioEmocoesRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public DadosDetalhamentoDiario salvarSonhos(DadosCadastroDiario dados){

        var paciente = pacienteRepository.getReferenceById(dados.paciente());

        LocalDate hoje = LocalDate.now();
        LocalTime meioDia = LocalTime.NOON;
        LocalDateTime data = LocalDateTime.of(hoje, meioDia);

        if(diarioSonhosRepository.existsByData(data)){
            var diarioSonhos = diarioSonhosRepository.findByData(data);
            diarioSonhos.setTexto(dados.texto());
            diarioSonhos.setTitulo(dados.titulo());
        }else{
            diarioSonhosRepository.save(new DiarioSonhos(dados, paciente));
        }

        return null;
    }

    public DadosDetalhamentoDiario salvarEmocoes(DadosCadastroDiario dados){

        var paciente = pacienteRepository.getReferenceById(dados.paciente());

        LocalDate hoje = LocalDate.now();
        LocalTime meioDia = LocalTime.NOON;
        LocalDateTime data = LocalDateTime.of(hoje, meioDia);

        if(diarioEmocoesRepository.existsByData(data)){
            var diarioEmocoes = diarioEmocoesRepository.findByData(data);
            diarioEmocoes.setTexto(dados.texto());
            diarioEmocoes.setTitulo(dados.titulo());
            diarioEmocoesRepository.save(diarioEmocoes);
        }else{
            diarioEmocoesRepository.save(new DiarioEmocoes(dados, paciente));
        }

        return null;
    }

    public DiarioSonhos obterSonhos(LocalDateTime data) {
        return diarioSonhosRepository.findByData(data);
    }

    public DiarioEmocoes obterEmocoes(LocalDateTime data) {
        return diarioEmocoesRepository.findByData(data);
    }

    public LocalDateTime converterData(String dataString){

        LocalDate data = LocalDate.parse(dataString);
        LocalTime meioDia = LocalTime.NOON;

        LocalDateTime dataMeioDia = LocalDateTime.of(data, meioDia);

        return dataMeioDia;
    }
}
