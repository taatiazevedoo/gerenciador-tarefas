package com.gerenciador.tarefas.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarefaRequest {

    private String titulo;
    private String descricao;
    private Long criador;
    private int quantidadeHorasEstimada;
}
