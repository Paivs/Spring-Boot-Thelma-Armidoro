package br.thelma.armidoro.api.domain.diarios;

import java.time.LocalDateTime;

public record DadosDetalhamentoDiario(
        String titulo,
        String texto,
        LocalDateTime data
) {
    public DadosDetalhamentoDiario(DiarioSonhos diario){
        this(diario.getTitulo(), diario.getTexto(), diario.getData());
    }
    public DadosDetalhamentoDiario(DiarioEmocoes diario){
        this(diario.getTitulo(), diario.getTexto(), diario.getData());
    }
}
