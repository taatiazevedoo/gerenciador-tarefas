package com.gerenciador.tarefas.service;

import com.gerenciador.tarefas.entity.Tarefa;
import com.gerenciador.tarefas.excecoes.NaoPermitidoAlterarStatusException;
import com.gerenciador.tarefas.excecoes.NaoPermitidoExcluirException;
import com.gerenciador.tarefas.excecoes.TarefaExistenteException;
import com.gerenciador.tarefas.repository.ITarefaRepository;
import com.gerenciador.tarefas.request.AtualizarTarefaRequest;
import com.gerenciador.tarefas.request.CadastrarTarefaRequest;
import com.gerenciador.tarefas.status.TarefaStatusEnum;
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

    @Autowired
    private MessageService messageService;

    public Tarefa salvarTarefa(CadastrarTarefaRequest request) {
        validarSalvarTarefa(request);

        Tarefa tarefa = Tarefa.builder()
                .titulo(request.getTitulo())
                .descricao(request.getDescricao())
                .status(TarefaStatusEnum.CRIADA)
                .quantidadeHorasEstimada(request.getQuantidadeHorasEstimada())
                .criador(usuarioService.obterUsuarioPorId(request.getCriador()).get())
                .build();

        return tarefaRepository.save(tarefa);
    }

    public Page<Tarefa> obtemTarefasPorTitulo(String titulo, Pageable pageable) {
        return this.tarefaRepository.findByTituloContainingOrderByDataAtualizacaoDesc(titulo, pageable);
    }

    public Page<Tarefa> obtemTodasTarefas(Pageable pageable) {
        return this.tarefaRepository.findAllByOrderByDataAtualizacaoDesc(pageable);
    }

    public Tarefa atualizarTarefa(Long idTarefa, AtualizarTarefaRequest request) {
        Tarefa tarefa = this.tarefaRepository.findById(idTarefa).get();

        validarAtualizacao(tarefa, request);

        tarefa.setTitulo(request.getTitulo());
        tarefa.setDescricao(request.getDescricao());
        tarefa.setStatus(request.getStatus());
        tarefa.setQuantidadeHorasEstimada(request.getQuantidadeHorasEstimada());
        tarefa.setResponsavel(usuarioService.obterUsuarioPorId(request.getResponsavel()).get());
        tarefa.setQuantidadeHorasRealizada(request.getQuantidadeHorasRealizada());

        return tarefaRepository.save(tarefa);
    }

    private void validarSalvarTarefa(CadastrarTarefaRequest request) {
        Tarefa tarefaValidacao = tarefaRepository.findByTitulo(request.getTitulo());

        if (tarefaValidacao != null) {
            throw new TarefaExistenteException(messageService.getMessage(
                    "msg.validacao.nao.permitido.tarefa.duplicada.exception"));
        }
    }

    private void validarAtualizacao(Tarefa tarefa, AtualizarTarefaRequest request) {
        if (tarefa.getStatus().equals(TarefaStatusEnum.FINALIZADA)) {
            String mensagem = messageService.getMessage(
                    "msg.validacao.nao.permitido.alterar.tarefa.finalizada.exception",
                    new Object[]{
                            TarefaStatusEnum.FINALIZADA.toString(),
                    }
            );

            throw new NaoPermitidoAlterarStatusException(mensagem);
        }

        if (tarefa.getStatus().equals(TarefaStatusEnum.CRIADA) &&
                request.getStatus().equals(TarefaStatusEnum.FINALIZADA)) {
            String mensagem = getMensagemNaoPermitidoAlterarStatusException(
                    TarefaStatusEnum.FINALIZADA.toString(),
                    TarefaStatusEnum.CRIADA.toString()
            );

            throw new NaoPermitidoAlterarStatusException(mensagem);
        }

        if (tarefa.getStatus().equals(TarefaStatusEnum.BLOQUEADA) &&
                request.getStatus().equals(TarefaStatusEnum.FINALIZADA)) {
            String mensagem = getMensagemNaoPermitidoAlterarStatusException(
                    TarefaStatusEnum.BLOQUEADA.toString(),
                    TarefaStatusEnum.FINALIZADA.toString()
            );

            throw new NaoPermitidoAlterarStatusException(mensagem);
        }
    }

    private String getMensagemNaoPermitidoAlterarStatusException(String statusAtual,
                                                                 String novoStatus) {
        return messageService.getMessage(
                "msg.validacao.nao.permitido.alterar.status.exception",
                new Object[]{
                        statusAtual,
                        novoStatus
                }
        );
    }

    public void excluirTarefa(Long idTarefa) {
        Tarefa tarefa = this.tarefaRepository.findById(idTarefa).get();

        if (!TarefaStatusEnum.CRIADA.equals(tarefa.getStatus())) {
            throw new NaoPermitidoExcluirException();
        }

        this.tarefaRepository.deleteById(idTarefa);
    }
}
