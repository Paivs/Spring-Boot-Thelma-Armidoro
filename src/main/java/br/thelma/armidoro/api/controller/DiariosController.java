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
        } else if (dados.tipo()) { // emocoes
            diario = diarioServices.salvarEmocoes(dados);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sonhos/{data}")
    public ResponseEntity requisitarDiarioSonhos(@PathVariable String data){
        LocalDateTime dataFormatada = diarioServices.converterData(data);
        DadosDetalhamentoDiario retorno = new DadosDetalhamentoDiario(diarioServices.obterSonhos(dataFormatada));
        return ResponseEntity.ok(retorno);
    }

    @GetMapping("/emocoes/{data}")
    public ResponseEntity requisitarDiarioEmocoes(@PathVariable String data){
        LocalDateTime dataFormatada = diarioServices.converterData(data);
        DadosDetalhamentoDiario retorno = new DadosDetalhamentoDiario(diarioServices.obterEmocoes(dataFormatada));
        return ResponseEntity.ok(retorno);
    }
}
