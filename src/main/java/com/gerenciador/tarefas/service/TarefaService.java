package com.gerenciador.tarefas.service;

import com.gerenciador.tarefas.entity.Tarefa;
import com.gerenciador.tarefas.repository.ITarefaRepository;
import com.gerenciador.tarefas.request.TarefaRequest;
import com.gerenciador.tarefas.status.TarefaSatusEnum;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TarefaService {

    @Autowired
    private ITarefaRepository tarefaRepository;

    @Autowired
    private UsuarioService usuarioService;

    public Tarefa salvarTarefa(TarefaRequest request) {
        Tarefa tarefa = Tarefa.builder()
                .titulo(request.getTitulo())
                .descricao(request.getDescricao())
                .status(TarefaSatusEnum.CRIADA)
                .quantidadeHorasEstimada(request.getQuantidadeHorasEstimada())
                .criador(usuarioService.obterUsuarioPorId(request.getCriador()).get())
                .build();

        return tarefaRepository.save(tarefa);
    }
}
