package br.thelma.armidoro.api.domain.diarios;

import br.thelma.armidoro.api.domain.consulta.MotivoCancelamento;
import br.thelma.armidoro.api.domain.medico.Medico;
import br.thelma.armidoro.api.domain.paciente.Paciente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Table(name = "diarioemocoes")
@Entity(name = "DiarioEmocoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class DiarioEmocoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime data;

    private String titulo;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String texto;

    public DiarioEmocoes(DadosCadastroDiario dados, Paciente paciente) {
        LocalDate hoje = LocalDate.now();
        LocalTime meioDia = LocalTime.NOON;
        LocalDateTime dataMeioDia = LocalDateTime.of(hoje, meioDia);

        this.data = dataMeioDia;

        this.titulo = dados.titulo();
        this.texto = dados.texto();

        this.paciente = paciente;
    }
}
