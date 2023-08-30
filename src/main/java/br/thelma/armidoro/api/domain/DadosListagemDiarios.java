package br.thelma.armidoro.api.domain;

import br.thelma.armidoro.api.domain.diarios.DiarioEmocoes;
import br.thelma.armidoro.api.domain.diarios.DiarioSonhos;

import java.time.LocalDateTime;

public record DadosListagemDiarios(
        String titulo,
        String texto,
        LocalDateTime data
) {
    public DadosListagemDiarios(DiarioEmocoes t) {
        this(t.getTitulo(), t.getTexto(), t.getData());
    }

    public DadosListagemDiarios(DiarioSonhos t) {
        this(t.getTitulo(), t.getTexto(), t.getData());
    }
}
