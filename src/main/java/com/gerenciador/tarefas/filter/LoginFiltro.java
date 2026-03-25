package com.gerenciador.tarefas.filter;

import com.gerenciador.tarefas.entity.UsuarioAutenticado;
import com.gerenciador.tarefas.service.AutenticacaoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

public class LoginFiltro extends AbstractAuthenticationProcessingFilter {

    public LoginFiltro(String url, AuthenticationManager authenticationManager) {
        super(url, authenticationManager);
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String collect = request.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));

        UsuarioAutenticado usuarioAutenticado = new ObjectMapper().readValue(collect, UsuarioAutenticado.class);

        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(
                        usuarioAutenticado.getUsuario(),
                        usuarioAutenticado.getSenha(),
                        Collections.emptyList()
                ));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest httpServletRequest,
                                            HttpServletResponse httpServletResponse,
                                            FilterChain filterChain,
                                            Authentication authentication) {
        AutenticacaoService.addJWTToken(httpServletResponse, authentication);
    }
}
