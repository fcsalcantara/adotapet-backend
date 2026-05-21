package br.estacio.adotapet.backend.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Tipos das exceções da API, com suas respectivas mensagens, que serão retornadas ao cliente.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ExcecaoApiEnum {

    FalhaAutenticacaoAcessoApi("Falha na autenticação do acesso ao serviço.");

    private final String mensagem;
}
