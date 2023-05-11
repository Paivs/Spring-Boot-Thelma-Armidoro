package br.thelma.armidoro.api.controller;

import br.thelma.armidoro.api.domain.cadastro.*;
import br.thelma.armidoro.api.domain.usuario.DadosAlterarSenha;
import br.thelma.armidoro.api.domain.usuario.DadosCadastroUsuario;
import br.thelma.armidoro.api.domain.usuario.Usuario;
import br.thelma.armidoro.api.domain.usuario.UsuarioRepository;
import br.thelma.armidoro.api.services.EmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/cadastrar")
@SecurityRequirement(name = "bearer-key")
public class CadastroController {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    public EmailService emailService;

    @Autowired
    public AcessoCadastroRepository acessoCadastroRepository;

    @Autowired
    public AcessoSenhaRepository acessoSenhaRepository;

    @PostMapping("/alterar")
    public ResponseEntity mudarSenha(@RequestBody DadosAlterarSenha dados){
        boolean bate = false;
        try{ bate = acessoSenhaRepository.findPorPin(dados.login(), dados.pin().pin()).estaInvalido(LocalDateTime.now());
        } catch(Exception e){ System.out.println(e); }

        if(bate){
            var encripta = new BCryptPasswordEncoder();
            var senhaEncrp = encripta.encode(dados.senha());

            var id = usuarioRepository.findUsuarioPorNome(dados.login()).getId();
            var user = usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
            user.setSenha(senhaEncrp);
            usuarioRepository.save(user);

            return ResponseEntity.ok(new Usuario(dados));
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

    @PutMapping("/alterar/{login}")
    public ResponseEntity mudarSenhaPIN(@PathVariable String login){
        //se existir um usuario com aquele email
        if (!usuarioRepository.findUsuariosPorNome(login).isEmpty()) {

            //Set todos as requisições anteriores para false
            acessoSenhaRepository.findSeJaGerou(login).forEach(c -> {
                acessoSenhaRepository.getReferenceById(c.getId()).setAtivo(false);
            });

            var acessoSenha = new AcessoSenha(new DadosCadastroAcessoSenha(login));
            acessoSenha.enviarPinSenha(emailService);
            acessoSenhaRepository.save(acessoSenha);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("""
[
    {                        
        "campo": "Login",
        "mensagem": "Esse email não está cadastrado"
    }
]""");
        }
    }

    @PostMapping
    public ResponseEntity efetuarRequisiçãoCadastro(@RequestBody @Valid DadosCadastroUsuario novoUsuario) {

        //se não já existir um usuario com aquele email
        if (usuarioRepository.findUsuariosPorNome(novoUsuario.login()).isEmpty()) {

            //Set todos as requisições anteriores para false
            acessoCadastroRepository.findSeJaGerou(novoUsuario.login()).forEach(c -> {
                acessoCadastroRepository.getReferenceById(c.getId()).setAtivo(false);
            });

            var acessoCadastro = new AcessoCadastro(new DadosCadastroAcessoCadastro(novoUsuario.login()));
            acessoCadastro.enviarPin(emailService);
            acessoCadastroRepository.save(acessoCadastro);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("""
[
    {                        
        "campo": "Login",
        "mensagem": "Esse email já está cadastrado"
    }
]""");
        }
    }

    @PutMapping
    public ResponseEntity efetuarCadastro(@RequestBody @Valid DadosCadastroPin dados){
        boolean bate = false;
        try{ bate = acessoCadastroRepository.findPorPin(dados.pin().login(), dados.pin().pin()).estaInvalido(LocalDateTime.now());
        } catch(Exception e){ System.out.println(e); }

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
