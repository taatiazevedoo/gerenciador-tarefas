package com.gerenciador.tarefas.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AutenticacaoService {

    private static final String JWT_KEY = "signinKey";
    private static final String BEARER = "Bearer";
    private static final String AUTHORITIES = "authorities";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final int EXPIRATION_TOKEN_ONE_HOUR = 3600000;

    @Autowired
    private static MessageService messageService;

    static public void addJWTToken(HttpServletResponse response, Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();

        List<String> authoritiesList = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        claims.put(AUTHORITIES, authoritiesList);

        String jwtToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN_ONE_HOUR))
                .signWith(SignatureAlgorithm.HS512, JWT_KEY)
                .addClaims(claims)
                .compact();

        response.addHeader(HEADER_AUTHORIZATION, BEARER + " " + jwtToken);
        response.addHeader("Access-Control-Expose-Headers", HEADER_AUTHORIZATION);
    }

    static public Authentication obterAutenticacao(HttpServletRequest request) {
        String token = request.getHeader(HEADER_AUTHORIZATION);

        if (token != null) {
            Claims usuario = Jwts.parser()
                    .setSigningKey(JWT_KEY)
                    .parseClaimsJws(token.replace(BEARER + " ", ""))
                    .getBody();

            if (usuario != null) {
                List<SimpleGrantedAuthority> permissoes = ((ArrayList<String>) usuario.get(AUTHORITIES))
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                return new UsernamePasswordAuthenticationToken(usuario, null, permissoes);
            } else {
                String msgException = messageService.getMessage("msg.autenticacao.falha.conexao");

                throw new RuntimeException(msgException);
            }
        }

        return null;
    }
}
