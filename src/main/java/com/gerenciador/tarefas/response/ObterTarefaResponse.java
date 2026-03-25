package com.gerenciador.tarefas.response;

import com.gerenciador.tarefas.status.TarefaStatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
public class ObterTarefaResponse {

    private long id;
    private String titulo;
    private String descricao;
    private TarefaStatusEnum status;
    private String responsavel;
    private String criador;
    private int quantidadeHorasEstimada;
    private Integer quantidadeHorasRealizada;
    private LocalTime dataCadastro;
    private LocalTime dataAtualizacao;
}
