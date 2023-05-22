package br.thelma.armidoro.api.controller;

import br.thelma.armidoro.api.domain.paciente.DadosDetalhamentoPaciente;
import br.thelma.armidoro.api.domain.paciente.PacienteRepository;
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

    @Autowired
    PacienteRepository pacienteRepository;

    @PutMapping
    public ResponseEntity alterarSenha(@RequestBody @Valid DadosAlterarPerfil dados) throws Exception {

        var user = usuarioRepository.findOneUsuariosPorNome(dados.login());
        if(user.getSenha().equals(dados.senhaAntiga())){
            usuarioRepository.findOneUsuariosPorNome(dados.login()).setSenha(dados.senhaNova());
            usuarioRepository.findOneUsuariosPorNome(dados.login()).setLogin(dados.login());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{login}")
    public ResponseEntity getInfo(@PathVariable String login) throws Exception {

        var email = login;

        try{
            var resul = pacienteRepository.findByEmail(email);
            return ResponseEntity.ok().body(new DadosDetalhamentoPaciente(resul));
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
