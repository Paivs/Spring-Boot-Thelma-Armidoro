package br.thelma.armidoro.api.controller;

import br.thelma.armidoro.api.domain.DadosListagemDiarios;
import br.thelma.armidoro.api.domain.diarios.*;
import br.thelma.armidoro.api.domain.paciente.PacienteRepository;
import br.thelma.armidoro.api.services.DiarioServices;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("diarios")
@SecurityRequirement(name = "bearer-key")
public class DiariosController {

    @Autowired
    private DiarioServices diarioServices;

    @Autowired
    private DiarioEmocoesRepository diarioEmocoesRepository;

    @Autowired
    private DiarioSonhosRepository diarioSonhosRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    public ResponseEntity cadastrarDiario(@RequestBody @Valid DadosCadastroDiario dados){
        DadosDetalhamentoDiario diario;
        if(!dados.tipo()){ // sonhos
            diario = diarioServices.salvarSonhos(dados);
            return ResponseEntity.ok(diario);
        } else if (dados.tipo()) { // emocoes
            diario = diarioServices.salvarEmocoes(dados);
            return ResponseEntity.ok(diario);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/sonhos/{id}/{data}")
    public ResponseEntity requisitarDiarioSonhos(@PathVariable String id, @PathVariable String data){

        var encontrado = diarioServices.obterSonhos(data, id);

        if(encontrado == null){
            System.out.println("Emoção não encontrada!");
            return ResponseEntity.badRequest().build();
        }else{
            DadosDetalhamentoDiario retorno = new DadosDetalhamentoDiario(encontrado);
            return ResponseEntity.ok(retorno);
        }
    }

    @GetMapping("/emocoes/{id}/{data}")
    public ResponseEntity requisitarDiarioEmocoes(@PathVariable String id, @PathVariable String data){
        var  encontrado = diarioServices.obterEmocoes(data, id);

        if(encontrado == null){
            System.out.println("Emoção não encontrada!");
            return ResponseEntity.badRequest().build();
        }else{
            DadosDetalhamentoDiario retorno = new DadosDetalhamentoDiario(encontrado);
            return ResponseEntity.ok(retorno);
        }
    }

    @GetMapping("/emocoes/listar/{email}")
    public ResponseEntity<Page<DadosListagemDiarios>> listarEmocoes(@PageableDefault(size = 10) Pageable paginacao, @PathVariable String email) {
        System.out.println("LISTANDO DE " + email);
        var page = diarioEmocoesRepository.encontrarPorPaciente(pacienteRepository.findByEmail(email).getId(), paginacao).map(t -> new DadosListagemDiarios(t));
        return ResponseEntity.ok(page);
    }

    @GetMapping("/sonhos/listar/{email}")
    public ResponseEntity<Page<DadosListagemDiarios>> listarSonhos(@PageableDefault(size = 10) Pageable paginacao, @PathVariable String email) {
        System.out.println("LISTANDO DE " + email);
        var page = diarioSonhosRepository.encontrarPorPaciente(pacienteRepository.findByEmail(email).getId(), paginacao).map(t -> new DadosListagemDiarios(t));
        return ResponseEntity.ok(page);
    }
}
