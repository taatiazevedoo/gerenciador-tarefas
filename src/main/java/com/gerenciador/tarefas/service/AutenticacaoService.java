package com.gerenciador.tarefas.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AutenticacaoService {

    public static final String SIGNIN_KEY = "signinKey";
    public static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION = "Authorization";

    static public void addJWTToken(HttpServletResponse response, String usuario) {
        String jwtToken = Jwts.builder()
                .setSubject(usuario)
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS512, SIGNIN_KEY)
                .compact();

        response.addHeader(AUTHORIZATION, BEARER + jwtToken);
        response.addHeader("Access-Control-Expose-Headers", AUTHORIZATION);
    }

    static public Authentication obterAutenticacao(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);

        if (token != null) {
            Claims usuario = Jwts.parser()
                    .setSigningKey(SIGNIN_KEY)
                    .parseClaimsJws(token.replace(BEARER, ""))
                    .getBody();

            if (usuario != null) {
                new UsernamePasswordAuthenticationToken(usuario, null, null);
            } else {
                throw new RuntimeException("Autenticação falhou!");
            }
        }

        return null;
    }
}
