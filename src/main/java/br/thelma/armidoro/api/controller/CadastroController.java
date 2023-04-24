package br.thelma.armidoro.api.controller;

import br.thelma.armidoro.api.domain.cadastro.*;
import br.thelma.armidoro.api.domain.usuario.DadosCadastroUsuario;
import br.thelma.armidoro.api.domain.usuario.Usuario;
import br.thelma.armidoro.api.domain.usuario.UsuarioRepository;
import br.thelma.armidoro.api.services.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cadastrar")
public class CadastroController {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    public EmailService emailService;

    @Autowired
    public AcessoCadastroRepository acessoCadastroRepository;

    @PostMapping
    public ResponseEntity efetuarRequisiçãoCadastro(@RequestBody @Valid DadosCadastroUsuario novoUsuario) {

        if (usuarioRepository.findUsuariosPorNome(novoUsuario.login()).isEmpty()) {

            acessoCadastroRepository.findSeJaGerou(novoUsuario.login()).forEach(c -> {
                acessoCadastroRepository.getReferenceById(c.getId()).setAtivo(false);
            });

            var acessoCadastro = new AcessoCadastro(new DadosCadastroAcessoCadastro(novoUsuario.login()));
            acessoCadastro.enviarPin(emailService);
            acessoCadastroRepository.save(acessoCadastro);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity efetuarCadastro(@RequestBody @Valid DadosCadastroPin dados){
        boolean bate = ! acessoCadastroRepository.findPorPin(dados.pin().login(), dados.pin().pin())
                .stream()
                .filter(c -> {
            return emailService.estaInvalido(c.getData());
                }).toList().isEmpty();

        if(bate){
            usuarioRepository.save(new Usuario(dados.usuario()));

            return ResponseEntity.ok(dados.usuario());
        }else if(!bate) {
            return ResponseEntity.badRequest().body("""
[
    {                        
        "campo": "Data",
        "mensagem": "Tempo para inserir o PIN expirado"
    }
]""");
        }
        else{
                return ResponseEntity.badRequest().build();
            }

    }

}
