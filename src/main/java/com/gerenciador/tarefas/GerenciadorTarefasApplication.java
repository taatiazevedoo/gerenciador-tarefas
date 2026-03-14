package com.gerenciador.tarefas;

import com.gerenciador.tarefas.entity.Regra;
import com.gerenciador.tarefas.entity.Usuario;
import com.gerenciador.tarefas.permissoes.PermissaoEnum;
import com.gerenciador.tarefas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class GerenciadorTarefasApplication implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    public static void main(String[] args) {
        SpringApplication.run(GerenciadorTarefasApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Usuario usuario = criarUsuarioAdmin();
        usuarioService.salvarUsuario(usuario);
    }

    private Usuario criarUsuarioAdmin() {
        Usuario usuario = new Usuario();
        usuario.setUsuario("admin");
        usuario.setSenha("123456");

        List<Regra> regras = new ArrayList<>();

        Regra regraAdmin = new Regra();
        regraAdmin.setNome(PermissaoEnum.ADMINISTRADOR);
        regras.add(regraAdmin);

        Regra regraUsuario = new Regra();
        regraUsuario.setNome(PermissaoEnum.USUARIO);
        regras.add(regraUsuario);

        usuario.setRegras(regras);

        return usuario;
    }
}
