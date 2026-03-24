package com.gerenciador.tarefas.service;

import com.gerenciador.tarefas.entity.Tarefa;
import com.gerenciador.tarefas.repository.ITarefaRepository;
import com.gerenciador.tarefas.request.AtualizarTarefaRequest;
import com.gerenciador.tarefas.request.CadastrarTarefaRequest;
import com.gerenciador.tarefas.status.TarefaSatusEnum;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TarefaService {

    @Autowired
    private ITarefaRepository tarefaRepository;

    @Autowired
    private UsuarioService usuarioService;

    public Tarefa salvarTarefa(CadastrarTarefaRequest request) {
        Tarefa tarefa = Tarefa.builder()
                .titulo(request.getTitulo())
                .descricao(request.getDescricao())
                .status(TarefaSatusEnum.CRIADA)
                .quantidadeHorasEstimada(request.getQuantidadeHorasEstimada())
                .criador(usuarioService.obterUsuarioPorId(request.getCriador()).get())
                .build();

        return tarefaRepository.save(tarefa);
    }

    public Page<Tarefa> obtemTarefasPorTitulo(String titulo, Pageable pageable) {
        return this.tarefaRepository.findByTituloContaining(titulo, pageable);
    }

    public Page<Tarefa> obtemTodasTarefas(Pageable pageable) {
        return this.tarefaRepository.findAll(pageable);
    }

    public Tarefa atualizarTarefa(Long id, AtualizarTarefaRequest request) {
        Tarefa tarefa = this.tarefaRepository.findById(id).get();

        tarefa.setTitulo(request.getTitulo());
        tarefa.setDescricao(request.getDescricao());
        tarefa.setStatus(request.getStatus());
        tarefa.setQuantidadeHorasEstimada(request.getQuantidadeHorasEstimada());
        tarefa.setResponsavel(usuarioService.obterUsuarioPorId(request.getResponsavel()).get());
        tarefa.setQuantidadeHorasRealizada(request.getQuantidadeHorasRealizada());

        return tarefaRepository.save(tarefa);
    }

    public void excluirTarefa(Long tarefaId) {
        this.tarefaRepository.deleteById(tarefaId);
    }
}
