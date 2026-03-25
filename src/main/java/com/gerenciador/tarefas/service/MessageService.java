package com.gerenciador.tarefas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageService {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String chave) {
        if (chave == null) {
            return null;
        }

        return messageSource.getMessage(chave, null, Locale.getDefault());
    }

    public String getMessage(String chave, Object[] objeto) {
        if (chave == null) {
            return null;
        }

        return messageSource.getMessage(chave, objeto, Locale.getDefault());
    }
}
