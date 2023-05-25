package br.thelma.armidoro.api.domain.diarios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface DiarioEmocoesRepository extends JpaRepository<DiarioEmocoes, Long> {

    boolean existsByData(LocalDateTime data);

    DiarioEmocoes findByData(LocalDateTime data);
}
