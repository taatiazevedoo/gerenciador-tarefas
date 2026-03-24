package com.gerenciador.tarefas.controller;

import com.gerenciador.tarefas.entity.Tarefa;
import com.gerenciador.tarefas.request.AtualizarTarefaRequest;
import com.gerenciador.tarefas.request.CadastrarTarefaRequest;
import com.gerenciador.tarefas.response.AtualizarTarefaResponse;
import com.gerenciador.tarefas.response.ObterTarefaResponse;
import com.gerenciador.tarefas.response.ObterTarefasPaginadaResponse;
import com.gerenciador.tarefas.response.CadastrarTarefaResponse;
import com.gerenciador.tarefas.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<CadastrarTarefaResponse> salvarTarefa(@Valid @RequestBody CadastrarTarefaRequest request) {
        Tarefa tarefaSalva = tarefaService.salvarTarefa(request);

        CadastrarTarefaResponse response = CadastrarTarefaResponse
                .builder()
                .id(tarefaSalva.getId())
                .titulo(tarefaSalva.getTitulo())
                .descricao(tarefaSalva.getDescricao())
                .criador(tarefaSalva.getCriador().getUsuario())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ObterTarefasPaginadaResponse> obterTarefas(
            @RequestParam(required = false) String titulo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        Page<Tarefa> tarefasPaginadas = null;

        Pageable pageable = PageRequest.of(page, size);

        if (titulo == null) {
            tarefasPaginadas = this.tarefaService.obtemTodasTarefas(pageable);
        } else {
            tarefasPaginadas = this.tarefaService.obtemTarefasPorTitulo(titulo, pageable);
        }

        List<ObterTarefaResponse> tarefas = tarefasPaginadas.getContent()
                .stream()
                .map(tarefa -> {
                    return ObterTarefaResponse.builder()
                            .id(tarefa.getId())
                            .titulo(tarefa.getTitulo())
                            .descricao(tarefa.getDescricao())
                            .responsavel(tarefa.getResponsavel() != null ? tarefa.getResponsavel().getUsuario() : "NAO_ATRIBUIDA")
                            .criador(tarefa.getCriador().getUsuario())
                            .status(tarefa.getStatus())
                            .quantidadeHorasEstimada(tarefa.getQuantidadeHorasEstimada())
                            .quantidadeHorasRealizada(tarefa.getQuantidadeHorasRealizada())
                            .dataCadastro(tarefa.getDataCadastro())
                            .dataAtualizacao(tarefa.getDataAtualizacao())
                            .build();
                })
                .toList();

        ObterTarefasPaginadaResponse response = ObterTarefasPaginadaResponse.builder()
                .paginaAtual(tarefasPaginadas.getNumber())
                .totalPaginas(tarefasPaginadas.getTotalPages())
                .totalItens(tarefasPaginadas.getTotalElements())
                .tarefas(tarefas)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void excluirTarefa(@PathVariable Long id) {
        tarefaService.excluirTarefa(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AtualizarTarefaResponse> atualizarTarefa(@PathVariable Long id,
                                                                   @Valid @RequestBody AtualizarTarefaRequest request) {
        Tarefa tarefaAtualizada = tarefaService.atualizarTarefa(id, request);

        AtualizarTarefaResponse response = AtualizarTarefaResponse.builder()
                .id(tarefaAtualizada.getId())
                .titulo(tarefaAtualizada.getTitulo())
                .descricao(tarefaAtualizada.getDescricao())
                .criador(tarefaAtualizada.getCriador().getUsuario())
                .quantidadeHorasEstimadas(tarefaAtualizada.getQuantidadeHorasEstimada())
                .status(tarefaAtualizada.getStatus().toString())
                .responsavel(tarefaAtualizada.getResponsavel().getUsuario())
                .quantidadeHorasRealizada(tarefaAtualizada.getQuantidadeHorasRealizada())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
