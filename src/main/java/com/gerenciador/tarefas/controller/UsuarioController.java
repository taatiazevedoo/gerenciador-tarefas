package com.gerenciador.tarefas.controller;

import com.gerenciador.tarefas.entity.Usuario;
import com.gerenciador.tarefas.service.MessageService;
import com.gerenciador.tarefas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    MessageService messageService;

    @PostMapping
    public ResponseEntity<String> salvarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);

        String msg = messageService.getMessage(
                "msg.usuario.salvo",
                new Object[]{
                        usuarioSalvo.getUsuario()
                }
        );

        return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> atualizarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioSalvo = usuarioService.atualizarUsuario(usuario);

        String msg = messageService.getMessage(
                "msg.usuario.atualizado",
                new Object[]{
                        usuarioSalvo.getUsuario()
                }
        );

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtemUsuarios() {
        return new ResponseEntity<>(usuarioService.obtemUsuarios(), HttpStatus.OK);
    }

    @DeleteMapping
    public void excluirUsuario(@RequestBody Usuario usuario) {
        usuarioService.excluirUsuario(usuario);
    }
}
