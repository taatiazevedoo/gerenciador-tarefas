package com.gerenciador.tarefas.service;

import com.gerenciador.tarefas.entity.Usuario;
import com.gerenciador.tarefas.repository.IUsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioAutenticadoService implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    public UserDetails loadUserByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário " +
                        username + " não foi encontrado"));

        List<SimpleGrantedAuthority> regras = usuario.getRegras()
                .stream()
                .map(regra -> new SimpleGrantedAuthority(regra.getNome().toString()))
                .collect(Collectors.toList());

        return new User(usuario.getUsuario(), usuario.getSenha(), regras);
    }
}
