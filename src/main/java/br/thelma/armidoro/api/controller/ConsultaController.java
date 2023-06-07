package br.thelma.armidoro.api.controller;

import br.thelma.armidoro.api.domain.consulta.*;
import br.thelma.armidoro.api.domain.paciente.DadosListagemPaciente;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @Autowired
    private ConsultaRepository consultaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoConsulta>> listar(@PageableDefault(size = 10, sort = "data") Pageable paginacao) {
        var page = consultaRepository.findAll(paginacao).map(DadosDetalhamentoConsulta::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<DadosDetalhamentoConsulta>> listarPorId(@PageableDefault(size = 10, sort = {"data"}) Pageable paginacao, @PathVariable Long id) {
        var page = consultaRepository.findAll(paginacao);//.map(DadosDetalhamentoConsulta::new);

        var resul = page.stream().filter(c -> c.getPaciente().getId() == id && c.getMotivoCancelamento() == null).map(DadosDetalhamentoConsulta::new).collect(Collectors.toList());

        return ResponseEntity.ok(new PageImpl<DadosDetalhamentoConsulta>(resul));
    }

    @GetMapping("contem/{id}")
    public ResponseEntity contemPorId(@PathVariable Long id) {
        var page = consultaRepository.findAll();

        var resul = page.stream().filter(c -> c.getPaciente().getId() == id && c.getMotivoCancelamento() == null).map(DadosDetalhamentoConsulta::new).collect(Collectors.toList());

        if(resul.isEmpty()){
            return ResponseEntity.badRequest().build();
        }else if(!resul.isEmpty()){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("tempo/{id}")
    public ResponseEntity tempoPorId(@PathVariable Long id) {
        var page = consultaRepository.findAll();

        var resul = page.stream().filter(c -> c.getPaciente().getId() == id && c.getMotivoCancelamento() == null).collect(Collectors.toList());

        Collections.sort(resul, Comparator.comparing(Consulta::getData));

        try{
        var dataConsulta = resul.stream().map(DadosDetalhamentoConsulta::new).findFirst()
                .orElse(null)
                .data();

        LocalDateTime now = LocalDateTime.now();

        var tempoParaConsulta = "";

        if (dataConsulta != null) {
            long minutosRestantes = now.until(dataConsulta, ChronoUnit.MINUTES);

            if (minutosRestantes < 60) {
                tempoParaConsulta = (minutosRestantes + " minutos");
            } else {
                long horasRestantes = now.until(dataConsulta, ChronoUnit.HOURS);

                if (horasRestantes < 24) {
                    long minutosExcedentes = minutosRestantes % 60;
                    tempoParaConsulta = (horasRestantes + " horas e " + minutosExcedentes + " minutos");
                } else {
                    long diasRestantes = now.until(dataConsulta, ChronoUnit.DAYS);

                    if (diasRestantes <= 28) {
                        tempoParaConsulta = (diasRestantes + " dias");
                    } else {
                        tempoParaConsulta = ("Mais de um mês");
                    }
                }
            }
        }else {
            return ResponseEntity.badRequest().body("Consulta não encontrada");
        }
        return ResponseEntity.ok().body("{\n\"valor\": \"" + tempoParaConsulta +"\"\n}");
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }


}
