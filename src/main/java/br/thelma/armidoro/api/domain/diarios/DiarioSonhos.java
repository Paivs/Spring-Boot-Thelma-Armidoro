package br.thelma.armidoro.api.domain.diarios;

import br.thelma.armidoro.api.domain.paciente.Paciente;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Table(name = "diariosonhos")
@Entity(name = "DiarioSonhos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class DiarioSonhos {

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

    public DiarioSonhos(DadosCadastroDiario dados, Paciente paciente) {
        LocalDate hoje = LocalDate.now();
        LocalTime meioDia = LocalTime.NOON;
        LocalDateTime dataMeioDia = LocalDateTime.of(hoje, meioDia);

        this.data = dataMeioDia;

        this.titulo = dados.titulo();
        this.texto = dados.texto();

        this.paciente = paciente;
    }
}
