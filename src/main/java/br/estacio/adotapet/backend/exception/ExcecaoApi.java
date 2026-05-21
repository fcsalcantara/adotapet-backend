package br.estacio.adotapet.backend.exception;

import lombok.Getter;

/**
 * Exceções lançadas para serem retornadas ao cliente da API.
 */
@Getter
public class ExcecaoApi extends RuntimeException {

    // Tipo de exceção lançada na API.
    private final String tipo;

    // Especifica a mensagem para exceção lançada.
    private final String mensagem;

    /**
     * Construtor.
     *
     * @param excecaoApiEnum Um dos tipos de exceção predefinidos na enum.
     */
    public ExcecaoApi(ExcecaoApiEnum excecaoApiEnum) {

        tipo = excecaoApiEnum.name();
        mensagem = excecaoApiEnum.getMensagem();
    }
}
