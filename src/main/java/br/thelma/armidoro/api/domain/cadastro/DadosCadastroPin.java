package br.thelma.armidoro.api.domain.cadastro;

import br.thelma.armidoro.api.domain.usuario.DadosCadastroUsuario;
import jakarta.validation.Valid;

public record DadosCadastroPin(
        @Valid
        DadosEnvioPin pin,

        @Valid
        DadosCadastroUsuario usuario) {
}
