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
        var page = consultaRepository.findAll(paginacao).map(DadosDetalhamentoConsulta::new);

        var resul = page.stream().filter(c -> c.idPaciente() == id).collect(Collectors.toList());

        return ResponseEntity.ok(new PageImpl<DadosDetalhamentoConsulta>(resul));
    }


}
