package br.estacio.adotapet.backend.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Contém os detalhes de um erro de validação de um determinado campo.
 *
 * @param atributoComErro O atributo/campo onde está o erro.
 * @param mensagemDeErro  A mensagem de erro.
 */
@Schema(description = "Retorna ao cliente erros de validação dos campos de um JSON enviado")
public record ErroValidacaoDto(
        @Schema(description = "O nome do atributo/campo com erro")
        String atributoComErro,

        @Schema(description = "A mensagem de erro do preenchimento do atributo/campo")
        String mensagemDeErro) {

}
