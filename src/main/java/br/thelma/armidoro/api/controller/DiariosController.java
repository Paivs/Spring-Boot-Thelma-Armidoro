package br.thelma.armidoro.api.controller;

import br.thelma.armidoro.api.domain.diarios.*;
import br.thelma.armidoro.api.services.DiarioServices;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("diarios")
@SecurityRequirement(name = "bearer-key")
public class DiariosController {

    @Autowired
    private DiarioServices diarioServices;

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
}
