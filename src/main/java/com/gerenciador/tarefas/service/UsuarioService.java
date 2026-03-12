package com.gerenciador.tarefas.service;

import com.gerenciador.tarefas.entity.Usuario;
import com.gerenciador.tarefas.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public Usuario salvarUsuario(Usuario usuario) {
        return this.usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Usuario usuario) {
        return this.usuarioRepository.save(usuario);
    }

    public void excluirUsuario(Usuario usuario) {
        this.usuarioRepository.deleteById(usuario.getId());
    }

    public List<Usuario> obtemUsuarios() {
        return this.usuarioRepository.findAll();
    }
}
