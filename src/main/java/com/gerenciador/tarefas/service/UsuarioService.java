package com.gerenciador.tarefas.service;

import com.gerenciador.tarefas.entity.Regra;
import com.gerenciador.tarefas.entity.Usuario;
import com.gerenciador.tarefas.repository.IRegraRepository;
import com.gerenciador.tarefas.repository.IUsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IRegraRepository regraRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario salvarUsuario(Usuario usuario) {
        List<Regra> regras = usuario.getRegras()
                .stream()
                .map(regra -> regraRepository.findByNome(regra.getNome()))
                .toList();

        usuario.setRegras(regras);
        codificarSenha(usuario);

        return this.usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Usuario usuario) {
        codificarSenha(usuario);

        return this.usuarioRepository.save(usuario);
    }

    public void excluirUsuario(Usuario usuario) {
        this.usuarioRepository.deleteById(usuario.getId());
    }

    public List<Usuario> obtemUsuarios() {
        return this.usuarioRepository.findAll();
    }

    private void codificarSenha(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
    }
}
