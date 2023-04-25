package br.thelma.armidoro.api.controller;

import br.thelma.armidoro.api.domain.usuario.DadosAlterarEmail;
import br.thelma.armidoro.api.domain.usuario.DadosAlterarSenha;
import br.thelma.armidoro.api.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @PutMapping
    public ResponseEntity alterarSenha(@RequestBody @Valid DadosAlterarPerfil dados) throws Exception {

        var user = usuarioRepository.findOneUsuariosPorNome(dados.login());
        if(user.getSenha().equals(dados.senhaAntiga())){
            usuarioRepository.findOneUsuariosPorNome(dados.login()).setSenha(dados.senhaNova());
            usuarioRepository.findOneUsuariosPorNome(dados.login()).setLogin(dados.login());
        }

        return ResponseEntity.ok().build();
    }

}
