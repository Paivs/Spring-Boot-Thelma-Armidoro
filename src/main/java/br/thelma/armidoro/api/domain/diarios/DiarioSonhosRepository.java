package br.thelma.armidoro.api.domain.diarios;

import br.thelma.armidoro.api.domain.paciente.Paciente;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface DiarioSonhosRepository extends JpaRepository<DiarioSonhos, Long> {

    boolean existsByDataAndPaciente(LocalDateTime data, Paciente paciente);

    DiarioSonhos findByDataAndPaciente(LocalDateTime data, Paciente paciente);

    @Query("""
            SELECT o from DiarioSonhos o
            WHERE 
            o.data = :data
            AND
            o.paciente = :id
            """)
    DiarioSonhos encontrarPorDataPaciente(LocalDateTime data, Paciente id);

    @Query("""
        SELECT o FROM DiarioSonhos o
        WHERE o.paciente.id = :id
        ORDER BY o.data DESC
            """)
    Page<DiarioSonhos> encontrarPorPaciente(Long id, Pageable paginacao);
}
