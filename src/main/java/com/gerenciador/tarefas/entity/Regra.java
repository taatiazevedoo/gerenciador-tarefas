package com.gerenciador.tarefas.entity;

import com.gerenciador.tarefas.permissoes.PermissaoEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="regra")
@Data
@Getter
@Setter
public class Regra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column
    @Enumerated(EnumType.STRING)
    private PermissaoEnum nome;

    @ManyToMany(mappedBy = "regras")
    private List<Usuario> usuarios;
}
