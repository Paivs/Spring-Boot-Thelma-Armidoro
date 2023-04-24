package br.thelma.armidoro.api.domain.contatoEmergencia;

import br.thelma.armidoro.api.domain.endereco.DadosEndereco;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContatoEmergencia {

    private String contato_nome;

    private String contato_vinculo;

    private String contato_telefone;

    public ContatoEmergencia(DadosContatoEmergencia dados) {
        this.contato_nome = dados.contato_nome();
        this.contato_vinculo = dados.contato_vinculo();
        this.contato_telefone = dados.contato_telefone();
    }

    public void atualizarInformacoes(DadosContatoEmergencia dados) {
        if (dados.contato_nome() != null) {
            this.contato_nome = dados.contato_nome();
        }
        if (dados.contato_vinculo() != null) {
            this.contato_vinculo = dados.contato_vinculo();
        }
        if (dados.contato_telefone() != null) {
            this.contato_telefone = dados.contato_telefone();
        }

    }
}
