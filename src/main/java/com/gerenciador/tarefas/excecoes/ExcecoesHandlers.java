package com.gerenciador.tarefas.excecoes;

import com.gerenciador.tarefas.response.ErrorResponse;
import com.gerenciador.tarefas.status.TarefaStatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExcecoesHandlers {

    public static final String CODIGO = "codigo";
    public static final String MENSAGEM = "mensagem";

    @ExceptionHandler(NaoPermitidoExcluirException.class)
    public ResponseEntity<ErrorResponse> naoPermitirExcluirExceptionHandler(NaoPermitidoExcluirException ex) {
        Map<String, String> response = new HashMap<>();

        response.put(CODIGO, ErrorsEnum.NAO_PERMITIDO_EXCLUIR.toString());
        response.put(MENSAGEM, "Não é permitido excluir uma tarefa com o status diferente de "
                + TarefaStatusEnum.CRIADA.toString());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.toString())
                .errors(Collections.singletonList(response))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NaoPermitidoAlterarStatusException.class)
    public ResponseEntity<ErrorResponse> naoPermitirAlterarStatusExceptionHandler(NaoPermitidoAlterarStatusException ex) {
        Map<String, String> response = new HashMap<>();

        response.put(CODIGO, ErrorsEnum.NAO_PERMITIDO_MUDAR_STATUS.toString());
        response.put(MENSAGEM, ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.toString())
                .errors(Collections.singletonList(response))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(TarefaExistenteException.class)
    public ResponseEntity<ErrorResponse> tarefaExistenteExceptionHandler(TarefaExistenteException ex) {
        Map<String, String> response = new HashMap<>();

        response.put(CODIGO, ErrorsEnum.TAREFA_EXISTENTE.toString());
        response.put(MENSAGEM, ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.toString())
                .errors(Collections.singletonList(response))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
