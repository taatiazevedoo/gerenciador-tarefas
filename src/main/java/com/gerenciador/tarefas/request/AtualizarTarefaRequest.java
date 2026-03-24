package com.gerenciador.tarefas.request;

import com.gerenciador.tarefas.status.TarefaSatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class AtualizarTarefaRequest {

    @NotBlank(message = "{atualizar.tarefa.request.titulo.obrigatorio}")
    private String titulo;

    @Length(max = 150, message = "{atualizar.tarefa.request.descricao.limite}")
    private String descricao;

    private TarefaSatusEnum status;

    private Long responsavel;

    @NotNull(message = "{atualizar.tarefa.request.quantidadeHorasEstimada.obrigatorio}")
    private Integer quantidadeHorasEstimada;

    private Integer quantidadeHorasRealizada;
}
