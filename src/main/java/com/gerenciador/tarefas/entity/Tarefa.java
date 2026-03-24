package com.gerenciador.tarefas.entity;

import com.gerenciador.tarefas.status.TarefaSatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "tarefa")
@Data
@Getter
@Setter
@Builder
public class Tarefa implements Serializable {

    private static final long serialVersionUID = 1L;

    public Tarefa() {
    }

    public Tarefa(long id,
                  String titulo,
                  String descricao,
                  TarefaSatusEnum status,
                  Usuario responsavel,
                  Usuario criador,
                  int quantidadeHorasEstimada,
                  Integer quantidadeHorasRealizada,
                  LocalTime dataCadastro,
                  LocalTime dataAtualizacao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.status = status;
        this.responsavel = responsavel;
        this.criador = criador;
        this.quantidadeHorasEstimada = quantidadeHorasEstimada;
        this.quantidadeHorasRealizada = quantidadeHorasRealizada;
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataAtualizacao;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TarefaSatusEnum status;

    @Column
    private Usuario responsavel;

    @Column(nullable = false)
    private Usuario criador;

    @Column(nullable = false)
    private int quantidadeHorasEstimada;

    @Column
    private Integer quantidadeHorasRealizada;

    @Column
    @CreationTimestamp
    private LocalTime dataCadastro;

    @Column
    @UpdateTimestamp
    private LocalTime dataAtualizacao;
}
