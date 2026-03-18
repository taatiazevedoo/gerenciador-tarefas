package com.gerenciador.tarefas.controller;

import com.gerenciador.tarefas.entity.Tarefa;
import com.gerenciador.tarefas.request.TarefaRequest;
import com.gerenciador.tarefas.response.TarefaResponse;
import com.gerenciador.tarefas.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<TarefaResponse> salvarTarefa(@RequestBody TarefaRequest tarefaRequest) {
        Tarefa tarefaSalva = tarefaService.salvarTarefa(tarefaRequest);

        return new ResponseEntity<>(createTarefaResponse(tarefaSalva), HttpStatus.CREATED);
    }

    private TarefaResponse createTarefaResponse(Tarefa tarefa) {
        return TarefaResponse.builder()
                .id(tarefa.getId())
                .titulo(tarefa.getTitulo())
                .descricao(tarefa.getDescricao())
                .criador(tarefa.getCriador().getUsuario())
                .build();
    }
}
