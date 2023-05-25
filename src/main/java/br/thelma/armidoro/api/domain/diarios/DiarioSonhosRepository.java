package br.thelma.armidoro.api.domain.diarios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface DiarioSonhosRepository extends JpaRepository<DiarioSonhos, Long> {

    boolean existsByData(LocalDateTime data);

    DiarioSonhos findByData(LocalDateTime data);
}
