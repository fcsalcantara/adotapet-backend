package br.estacio.adotapet.backend.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Contém os detalhes de uma exceção lançada pela API, que serão enviados ao cliente.
 *
 * @param excecao  Identificador da exceção lançada.
 * @param mensagem Mensagem correspondente à exceção lançada.
 */
@Schema(description = "Retorna ao cliente erros na utilização da API")
public record ExcecaoApiDto(
        @Schema(description = "A exceção correspondente ao erro")
        String excecao,

        @Schema(description = "A mensagem de erro")
        String mensagem) {

}
