package br.thelma.armidoro.api.domain.paciente;

import br.thelma.armidoro.api.domain.contatoEmergencia.ContatoEmergencia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import br.thelma.armidoro.api.domain.endereco.Endereco;

import java.time.LocalDateTime;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;

    private String telefone;

    private String cpf;

    @Embedded
    private Endereco endereco;

    private LocalDateTime nascimento;

    private String telefone_fixo;

    private Boolean ativo;

    private EstadoCivil estado_civil;

    private String curso;

    private String nacionalidade;

    private String profissao;

    private String cargo;

    @Embedded
    private ContatoEmergencia contatoEmergencia;

    private GrauEscolaridade escolaridade;

    public Paciente(DadosCadastroPaciente dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.telefone_fixo = dados.telefone_fixo();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());

        this.nascimento = dados.nascimento();
        this.nacionalidade = dados.nacionalidade();
        this.estado_civil = dados.estado_civil();
        this.curso = dados.curso();
        this.profissao = dados.profissao();
        this.cargo = dados.cargo();
        this.contatoEmergencia = new ContatoEmergencia(dados.contatoEmergencia());
    }

    public void atualizarInformacoes(DadosAtualizacaoPaciente dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.telefoneFixo() != null) {
            this.telefone_fixo = dados.telefoneFixo();
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarInformacoes(dados.endereco());
        }

    }

    public void excluir() {
        this.ativo = false;
    }
}
