package com.gerenciador.tarefas.repository;

import com.gerenciador.tarefas.entity.Regra;
import com.gerenciador.tarefas.permissoes.PermissaoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegraRepository extends JpaRepository<Regra, Long> {

    Regra findByNome(PermissaoEnum nome);
}
