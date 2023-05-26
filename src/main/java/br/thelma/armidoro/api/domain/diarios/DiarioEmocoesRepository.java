package br.thelma.armidoro.api.domain.diarios;

import br.thelma.armidoro.api.domain.paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface DiarioEmocoesRepository extends JpaRepository<DiarioEmocoes, Long> {

    boolean existsByDataAndPaciente(LocalDateTime data, Paciente paciente);

    DiarioEmocoes findByDataAndPaciente(LocalDateTime data, Paciente paciente);

    @Query("""
            SELECT o from DiarioEmocoes o
            WHERE 
            o.data = :data
            AND
            o.paciente = :id
            """)
    DiarioEmocoes encontrarPorDataPaciente(LocalDateTime data, Paciente id);
}
